package org.drools.runtime.process;

import java.util.Map;

public interface WorkItem {
	
	int PENDING = 0;
	int ACTIVE = 1;
	int COMPLETED = 2;
	int ABORTED = 3;

    long getId();
    
    String getName();
    
    int getState();
    
    Object getParameter(String name);
    Map<String, Object> getParameters();
    
    Object getResult(String name);
    Map<String, Object> getResults();

    long getProcessInstanceId();

}
