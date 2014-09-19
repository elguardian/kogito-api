package org.kie.internal.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * This class is a utility class for dynamically creating JPA queries.
 * </p>
 * See the jbpm-human-task-core and jbpm-audit *query() method logic.
 * </p>
 * This class is <em>not</em> thread-safe and should only be used locally in a method.
 */
public class QueryAndParameterAppender {

    private boolean startWithWhere = false;
    private boolean firstUse = true;
    private boolean alreadyUsed = false;
    private final StringBuilder queryBuilder;
    private final Map<String, Object> queryParams;

    private int queryParamId = 0;

    public QueryAndParameterAppender(StringBuilder queryBuilder, Map<String, Object> params) {
        this.queryBuilder = queryBuilder;
        this.queryParams = params;
    }

    public QueryAndParameterAppender(StringBuilder queryBuilder, Map<String, Object> params, boolean useWhere) {
        this.queryBuilder = queryBuilder;
        this.queryParams = params;
        this.startWithWhere = useWhere;
    }

    // "Normal" query parameters --------------------------------------------------------------------------------------------------

    public <T> void addQueryParameters( List<? extends Object> paramList, String listId, Class<T> type, String fieldName,
            String joinClause, boolean union ) {
        List<T> listIdParams;
        if( paramList != null && paramList.size() > 0 ) {
            Object inputObject = paramList.get(0);
            listIdParams = checkAndConvertListToType(paramList, inputObject, listId, type);
        } else {
            return;
        }
        String paramName = generateParamName();
        StringBuilder queryClause = new StringBuilder("( " + fieldName + " in (:" + paramName + ") ");
        if( joinClause != null ) {
            queryClause.append("and " + joinClause);
        }
        queryClause.append(" )");
        addToQueryBuilder(queryClause.toString(), union, listIdParams, paramName);
    }

    public <T> void addQueryParameters( Map<String, List<? extends Object>> inputParamsMap, String listId, Class<T> type,
            String fieldName, boolean union, String joinClause ) {
        List<? extends Object> inputParams = inputParamsMap.get(listId);
        addQueryParameters(inputParams, listId, type, fieldName, joinClause, union );
    }

    public <T> void addQueryParameters( List<? extends Object> inputParams, String listId, Class<T> type, String fieldName,
            boolean union ) {
        addQueryParameters(inputParams, listId, type, fieldName, null, union );
    }

    public <T> void addQueryParameters( Map<String, List<? extends Object>> inputParamsMap, String listId, Class<T> type,
            String fieldName, boolean union ) {
        List<? extends Object> inputParams = inputParamsMap.get(listId);
        addQueryParameters(inputParams, listId, type, fieldName, null, union );
    }

    // Range query parameters -----------------------------------------------------------------------------------------------------

    public <T> void addRangeQueryParameters(List<? extends Object> paramList, String listId, Class<T> type, String fieldName, String joinClause, boolean union ) {
        List<T> listIdParams;
        if( paramList != null && paramList.size() > 0 ) { 
            Object inputObject = paramList.get(0);
            if( inputObject == null ) { 
                inputObject = paramList.get(1);
                if( inputObject == null ) { 
                    return;
                }
            }  
            listIdParams = checkAndConvertListToType(paramList, inputObject, listId, type);
        } else { 
            return;
        }
        
        T min = listIdParams.get(0);
        T max = listIdParams.get(1);
        Map<String, T> paramNameMinMaxMap = new HashMap<String, T>(2);
        StringBuilder queryClause = new StringBuilder("( " );
        if( joinClause != null ) { 
           queryClause.append("( "); 
        } 
        queryClause.append(fieldName);
        if( min == null ) { 
          if( max == null ) { 
              return;
          } else { 
              // only max
              String maxParamName = generateParamName();
              queryClause.append(" <= :" + maxParamName + " " );
              paramNameMinMaxMap.put(maxParamName, max);
          }
        } else if( max == null ) { 
            // only min
            String minParamName = generateParamName();
            queryClause.append(" >= :" + minParamName + " ");
            paramNameMinMaxMap.put(minParamName, min);
        } else { 
            // both min and max
            String minParamName = generateParamName();
            String maxParamName = generateParamName();
            if( union ) { 
                queryClause.append(" >= :" + minParamName + " OR " + fieldName + " <= :" + maxParamName + " " );
            } else { 
                queryClause.append(" BETWEEN :" + minParamName + " AND :" + maxParamName + " " );
            }
            paramNameMinMaxMap.put(minParamName, min);
            paramNameMinMaxMap.put(maxParamName, max);
        } 
        if( joinClause != null ) { 
            queryClause.append(") and " + joinClause.trim() + " ");
        }
        queryClause.append(")");
        
        // add query string to query builder and fill params map
        internalAddToQueryBuilder(queryClause.toString(), union);
        for( Entry<String, T> nameMinMaxEntry : paramNameMinMaxMap.entrySet() ) { 
            queryParams.put(nameMinMaxEntry.getKey(), nameMinMaxEntry.getValue());
        }
        queryBuilderModificationCleanup();
    }

    public <T> void addRangeQueryParameters( Map<String, List<? extends Object>> inputParamsMap, String listId, Class<T> type,
            String fieldName, boolean union, String joinClause ) {
        List<? extends Object> inputParams = inputParamsMap.get(listId);
        addRangeQueryParameters(inputParams, listId, type, fieldName, joinClause, union );
    }

    public <T> void addRangeQueryParameters( List<? extends Object> inputParams, String listId, Class<T> type, String fieldName,
            boolean union ) {
        addRangeQueryParameters(inputParams, listId, type, fieldName, null, union);
    }

    public <T> void addRangeQueryParameters( Map<String, List<? extends Object>> inputParamsMap, String listId, Class<T> type,
            String fieldName, boolean union ) {
        List<? extends Object> inputParams = inputParamsMap.get(listId);
        addRangeQueryParameters(inputParams, listId, type, fieldName, null, union);
    }

    // Regex query parameters -----------------------------------------------------------------------------------------------------

    public void addRegexQueryParameters( List<String> inputParams, String listId, String fieldName, boolean union ) {
        addRegexQueryParameters(inputParams, listId, fieldName, null, union);
    }

    public void addRegexQueryParameters( List<String> paramValList, String listId, String fieldName, String joinClause, 
            boolean union) {
        // setup
        if( paramValList == null || paramValList.isEmpty() ) {
            return;
        }
        List<String> regexList = new ArrayList<String>(paramValList.size());
        for( String input : paramValList ) {
            if( input == null || input.isEmpty() ) {
                continue;
            }
            String regex = input.replace('*', '%').replace('.', '_');
            regexList.add(regex);
        }

        // build query string
        Map<String, String> paramNameRegexMap = new HashMap<String, String>();
        StringBuilder queryClause = new StringBuilder("( ");
        if( joinClause != null ) {
            queryClause.append("( ");
        }
        for( int i = 0; i < regexList.size(); ++i ) {
            String paramName = generateParamName();
            queryClause.append("(" + fieldName + " like :" + paramName + " ) ");
            paramNameRegexMap.put(paramName, regexList.get(i));
            if( i + 1 < regexList.size() ) {
                queryClause.append(union ? "or" : "and").append(" ");
            }
        }
        if( joinClause != null ) {
            queryClause.append(") and " + joinClause.trim() + " ");
        }
        queryClause.append(")");

        // add query string to query builder and fill params map
        internalAddToQueryBuilder(queryClause.toString(), union);
        for( Entry<String, String> nameRegexEntry : paramNameRegexMap.entrySet() ) {
            queryParams.put(nameRegexEntry.getKey(), nameRegexEntry.getValue());
        }
        queryBuilderModificationCleanup();
    }

    public boolean getFirstUse() {
        return this.firstUse;
    }

    // private helper methods =====================================================================================================

    private <T> void addToQueryBuilder( String query, boolean union, List<T> paramValList, String paramName ) {
        // modify query builder
        internalAddToQueryBuilder(query, union);
        // add query parameters
        Set<T> paramVals = new HashSet<T>(paramValList);
        queryParams.put(paramName, paramVals);
        // cleanup
        queryBuilderModificationCleanup();
    }

    private void internalAddToQueryBuilder( String query, boolean union ) {
        if( this.firstUse ) {
            if( startWithWhere ) {
                queryBuilder.append(" WHERE");
            } else {
                queryBuilder.append(" AND");
            }
            queryBuilder.append(" (");
            this.firstUse = false;
        } else if( this.alreadyUsed ) {
            queryBuilder.append(union ? "\nOR " : "\nAND ");
        }
        queryBuilder.append(query);
    }

    private void queryBuilderModificationCleanup() {
        this.alreadyUsed = true;
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> checkAndConvertListToType( List<?> inputList, Object inputObject, String field, Class<T> type ) {
        if( type.equals(inputObject.getClass()) ) {
            return (List<T>) inputList;
        } else {
            throw new IllegalArgumentException(field + " parameter is an instance of " + "List<"
                    + inputObject.getClass().getSimpleName() + "> instead of " + "List<" + type.getSimpleName() + ">");
        }
    }

    public String generateParamName() {
        int id = queryParamId++ % 26;
        char first = (char) ('A' + id);
        return new String(first + String.valueOf(((id + 1) / 26) + 1));
    }
}
