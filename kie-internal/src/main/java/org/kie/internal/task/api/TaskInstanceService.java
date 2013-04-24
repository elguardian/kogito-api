/*
 * Copyright 2013 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kie.internal.task.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.kie.api.task.model.I18NText;
import org.kie.api.task.model.OrganizationalEntity;
import org.kie.api.task.model.Task;
import org.kie.internal.task.api.model.ContentData;
import org.kie.internal.task.api.model.FaultData;
import org.kie.internal.task.api.model.SubTasksStrategy;

/**
 * The Task Instance Service is in charge of
 *  handling all the actions required to interact with a 
 *  Task Instance. All the operations described in the WS-HT specification
 *  related with the Task Lifecycle are implemented here.
 */
public interface TaskInstanceService {

    /**
     * LIFECYCLE METHODS
     *
     */
    long addTask(Task task, Map<String, Object> params);

    long addTask(Task task, ContentData data);

    void activate(long taskId, String userId);

    void claim(long taskId, String userId);

    void claim(long taskId, String userId, List<String> groupIds);

    void claimNextAvailable(String userId, String language);

    void claimNextAvailable(String userId, List<String> groupIds, String language);

    void complete(long taskId, String userId, Map<String, Object> data);

    void delegate(long taskId, String userId, String targetUserId);

    void exit(long taskId, String userId);

    void fail(long taskId, String userId, Map<String, Object> faultData);

    void forward(long taskId, String userId, String targetEntityId);

    void release(long taskId, String userId);

    void remove(long taskId, String userId);

    void resume(long taskId, String userId);

    void skip(long taskId, String userId);

    void start(long taskId, String userId);

    void stop(long taskId, String userId);

    void suspend(long taskId, String userId);

    void nominate(long taskId, String userId, List<OrganizationalEntity> potentialOwners);

    void setFault(long taskId, String userId, FaultData fault);

    void setOutput(long taskId, String userId, Object outputContentData);

    void deleteFault(long taskId, String userId);

    void deleteOutput(long taskId, String userId);

    void setPriority(long taskId, int priority);
    
    void setTaskNames(long taskId, List<I18NText> taskNames);
    
    void setExpirationDate(long taskId, Date date);
    
    public void setDescriptions(long taskId, List<I18NText> descriptions);
    
    public void setSkipable(long taskId, boolean skipable);
    
    void setSubTaskStrategy(long taskId, SubTasksStrategy strategy);
    
    int getPriority(long taskId);
    
    Date getExpirationDate(long taskId);
    
    List<I18NText> getDescriptions(long taskId);
    
    boolean isSkipable(long taskId);
    
    SubTasksStrategy getSubTaskStrategy(long taskId);
    
    
}
