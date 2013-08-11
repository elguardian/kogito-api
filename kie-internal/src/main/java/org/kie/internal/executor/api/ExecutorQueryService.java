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

import java.util.List;

/**
 * Executor query interface that provides runtime access to data.
 *
 */
public interface ExecutorQueryService {
    /**
     * Returns list of pending execution requests.
     * @return
     */
    List<RequestInfo> getPendingRequests();
    
    /**
     * Returns given pending request identified by <code>id</code>
     * @param id - unique id of the request
     * @return
     */
    List<RequestInfo> getPendingRequestById(Long id);
    
    /**
     * Returns request identified by <code>id</code> regardless of its status
     * @param id - unique id of the request
     * @return
     */
    RequestInfo getRequestById(Long id);
    
    /**
     * Returns requests identified by <code>businessKey</code> usually it should be only one with given 
     * business key but it does not have to as same business key requests can be processed sequentially and 
     * thus might be in different statuses.
     * @param businessKey - business key of the request
     * @return
     */
    List<RequestInfo> getRequestByBusinessKey(String businessKey);
    
    /**
     * Returns all errors (if any) for given request
     * @param id - unique id of the request
     * @return
     */
    List<ErrorInfo> getErrorsByRequestId(Long id);
    
    /**
     * Returns all queued requests
     * @return
     */
    List<RequestInfo> getQueuedRequests();
    
    /**
     * Returns all comleted requests.
     * @return
     */
    List<RequestInfo> getCompletedRequests();
    
    /**
     * Returns all requests that have errors.
     * @return
     */
    List<RequestInfo> getInErrorRequests();
    
    /**
     * Returns all requests that were cancelled
     * @return
     */
    List<RequestInfo> getCancelledRequests();
    
    /**
     * Returns all errors.
     * @return
     */
    List<ErrorInfo> getAllErrors(); 
    
    /**
     * Returns all requests
     * @return
     */
    List<RequestInfo> getAllRequests(); 
    
    /**
     * Returns all currently running requests
     * @return
     */
    List<RequestInfo> getRunningRequests();
    
    /**
     * Returns requests queued for future execution
     * @return
     */
    List<RequestInfo> getFutureQueuedRequests();
    
    /**
     * Returns requests based on their status
     * @param statuses - statuses that requests should be in
     * @return
     */
    List<RequestInfo> getRequestsByStatus(List<STATUS> statuses);
    
    /**
     * Dedicated method for handling special case that is get the request for processing.
     * To ensure its efficient use it shall perform necessary operation to minimize risk of 
     * race conditions and deadlock.
     * @return
     */
    RequestInfo getRequestForProcessing();
}
