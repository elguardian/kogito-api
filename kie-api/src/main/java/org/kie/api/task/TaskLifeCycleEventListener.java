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
package org.kie.api.task;

import java.util.EventListener;

public interface TaskLifeCycleEventListener extends EventListener {
    
    public void beforeTaskActivatedEvent(TaskEvent event);
    public void beforeTaskClaimedEvent(TaskEvent event);
    public void beforeTaskSkippedEvent(TaskEvent event);
    public void beforeTaskStartedEvent(TaskEvent event);
    public void beforeTaskStoppedEvent(TaskEvent event);
    public void beforeTaskCompletedEvent(TaskEvent event);
    public void beforeTaskFailedEvent(TaskEvent event);
    public void beforeTaskAddedEvent(TaskEvent event);
    public void beforeTaskExitedEvent(TaskEvent event);
    public void beforeTaskReleasedEvent(TaskEvent event);
    public void beforeTaskResumedEvent(TaskEvent event);
    public void beforeTaskSuspendedEvent(TaskEvent event);
    public void beforeTaskForwardedEvent(TaskEvent event);
    public void beforeTaskDelegatedEvent(TaskEvent event);
    public void beforeTaskNominatedEvent(TaskEvent event);
    
    public void afterTaskActivatedEvent(TaskEvent event);
    public void afterTaskClaimedEvent(TaskEvent event);
    public void afterTaskSkippedEvent(TaskEvent event);
    public void afterTaskStartedEvent(TaskEvent event);
    public void afterTaskStoppedEvent(TaskEvent event);
    public void afterTaskCompletedEvent(TaskEvent event);
    public void afterTaskFailedEvent(TaskEvent event);
    public void afterTaskAddedEvent(TaskEvent event);
    public void afterTaskExitedEvent(TaskEvent event);
    public void afterTaskReleasedEvent(TaskEvent event);
    public void afterTaskResumedEvent(TaskEvent event);
    public void afterTaskSuspendedEvent(TaskEvent event);
    public void afterTaskForwardedEvent(TaskEvent event);
    public void afterTaskDelegatedEvent(TaskEvent event);
    public void afterTaskNominatedEvent(TaskEvent event);
    
}
