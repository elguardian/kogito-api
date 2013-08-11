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

package org.kie.internal.executor.api;

import java.util.Date;
import java.util.List;


/**
 * Top level facade that aggregates operations defined in:
 * <ul>
 *  <li><code>Executor</code></li>
 *  <li><code>ExecutorQueryService</code></li>
 *  <li><code>ExecutorAdminService</code></li>
 * </ul>
 * @see Executor
 * @see ExecutorQueryService
 * @see ExecutorAdminService
 */
public interface ExecutorService {

    public List<RequestInfo> getQueuedRequests();

    public List<RequestInfo> getCompletedRequests();

    public List<RequestInfo> getInErrorRequests();

    public List<RequestInfo> getCancelledRequests();

    public List<ErrorInfo> getAllErrors();

    public List<RequestInfo> getAllRequests();
    
    public List<RequestInfo> getRequestsByStatus(List<STATUS> statuses);
    
    public List<RequestInfo> getRequestsByBusinessKey(String businessKey);

    public int clearAllRequests();

    public int clearAllErrors();

    public Long scheduleRequest(String commandName, CommandContext ctx);

    public void cancelRequest(Long requestId);

    public void init();

    public void destroy();
    
    public boolean isActive();

    public int getInterval();

    public void setInterval(int waitTime);

    public int getRetries();

    public void setRetries(int defaultNroOfRetries);

    public int getThreadPoolSize();

    public void setThreadPoolSize(int nroOfThreads);
    
    public List<RequestInfo> getPendingRequests();

    public List<RequestInfo> getPendingRequestById(Long id);

    public Long scheduleRequest(String commandId, Date date, CommandContext ctx);

    public List<RequestInfo> getRunningRequests();
    
    public List<RequestInfo> getFutureQueuedRequests();

    public RequestInfo getRequestById(Long requestId);

    public List<ErrorInfo> getErrorsByRequestId(Long requestId);
    
}
