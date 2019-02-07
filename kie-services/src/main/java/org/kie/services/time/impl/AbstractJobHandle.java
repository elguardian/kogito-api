/*
 * Copyright 2015 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
*/

package org.kie.services.time.impl;

import org.kie.services.time.JobHandle;

public abstract class AbstractJobHandle implements JobHandle {

    private JobHandle previous;
    private JobHandle next;

    public JobHandle getPrevious() {
        return previous;
    }

    public void setPrevious(JobHandle previous) {
        this.previous = previous;
    }

    public void nullPrevNext() {
        previous = null;
        next = null;
    }

    public void setNext(JobHandle next) {
        this.next = next;
    }

    public JobHandle getNext() {
        return next;
    }
}
