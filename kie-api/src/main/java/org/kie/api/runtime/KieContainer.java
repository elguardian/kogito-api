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

package org.kie.api.runtime;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.builder.ReleaseId;
import org.kie.api.builder.Results;

/**
 * A container for all the KieBases of a given KieModule
 */
public interface KieContainer {

    /**
     * Returns the ReleaseId of the KieModule wrapped by this KieContainer
     */
    ReleaseId getReleaseId();

    /**
     * Builds all the KieBase in the KieModule wrapped by this KieContainer
     * and return te Results of this building process
     */
    Results verify();

    /**
     * Updates this KieContainer to a KieModule with the given ReleaseId
     */
    void updateToVersion(ReleaseId version);

    /**
     * Returns the default KieBase in this KieContainer.
     * @throws RuntimeException if this KieContainer doesn't have any default KieBase
     * @see org.kie.api.builder.model.KieBaseModel#setDefault(boolean)
     */
    KieBase getKieBase();

    /**
     * Returns the KieBase with the given name in this KieContainer.
     * @throws RuntimeException if this KieContainer doesn't have any KieBase with the given name
     */
    KieBase getKieBase(String kBaseName);

    /**
     * Creates a new default KieBase using the given configuration.
     * @throws RuntimeException if this KieContainer doesn't have any default KieBase
     * @see org.kie.api.builder.model.KieBaseModel#setDefault(boolean)
     */
    KieBase newKieBase(KieBaseConfiguration conf);

    /**
     * Creates a new KieBase with the given name using the given configuration.
     * @throws RuntimeException if this KieContainer doesn't have any KieBase with the given name
     */
    KieBase newKieBase(String kBaseName, KieBaseConfiguration conf);

    /**
     * Creates the default KieSession for this KieContainer
     * @throws RuntimeException if this KieContainer doesn't have any default KieSession
     * @see org.kie.api.builder.model.KieSessionModel#setDefault(boolean)
     */
    KieSession newKieSession();

    /**
     * Creates the default KieSession for this KieContainer with the given configuration
     * @throws RuntimeException if this KieContainer doesn't have any default KieSession
     * @see org.kie.api.builder.model.KieSessionModel#setDefault(boolean)
     */
    KieSession newKieSession(KieSessionConfiguration conf);

    /**
     * Creates the default KieSession for this KieContainer using the given Environment
     * @throws RuntimeException if this KieContainer doesn't have any default KieSession
     * @see org.kie.api.builder.model.KieSessionModel#setDefault(boolean)
     */
    KieSession newKieSession(Environment environment);

    /**
     * Creates the default KieSession for this KieContainer with the given configuration and Environment
     * @throws RuntimeException if this KieContainer doesn't have any default KieSession
     * @see org.kie.api.builder.model.KieSessionModel#setDefault(boolean)
     */
    KieSession newKieSession(Environment environment, KieSessionConfiguration conf);

    /**
     * Creates the KieSession with the given name for this KieContainer
     * @throws RuntimeException if this KieContainer doesn't have any KieSession with the given name
     */
    KieSession newKieSession(String kSessionName);

    /**
     * Creates the KieSession with the given name for this KieContainer using the given Environment
     * @throws RuntimeException if this KieContainer doesn't have any KieSession with the given name
     */
    KieSession newKieSession(String kSessionName, Environment environment);

    /**
     * Creates the KieSession with the given name for this KieContainer with the given configuration
     * @throws RuntimeException if this KieContainer doesn't have any KieSession with the given name
     */
    KieSession newKieSession(String kSessionName, KieSessionConfiguration conf);

    /**
     * Creates the KieSession with the given name for this KieContainer using the given Environment and configuration
     * @throws RuntimeException if this KieContainer doesn't have any KieSession with the given name
     */
    KieSession newKieSession(String kSessionName, Environment environment, KieSessionConfiguration conf);

    /**
     * Creates the default StatelessKieSession for this KieContainer
     * @throws RuntimeException if this KieContainer doesn't have any default StatelessKieSession
     * @see org.kie.api.builder.model.KieSessionModel#setDefault(boolean)
     */
    StatelessKieSession newStatelessKieSession();

    /**
     * Creates the default StatelessKieSession for this KieContainer using the given configuration
     * @throws RuntimeException if this KieContainer doesn't have any default StatelessKieSession
     * @see org.kie.api.builder.model.KieSessionModel#setDefault(boolean)
     */
    StatelessKieSession newStatelessKieSession(KieSessionConfiguration conf);

    /**
     * Creates the StatelessKieSession with the given name for this KieContainer
     * @throws RuntimeException if this KieContainer doesn't have any StatelessKieSession with the given name
     */
    StatelessKieSession newStatelessKieSession(String kSessionName);

    /**
     * Creates the StatelessKieSession with the given name for this KieContainer using the given configuration
     * @throws RuntimeException if this KieContainer doesn't have any StatelessKieSession with the given name
     */
    StatelessKieSession newStatelessKieSession(String kSessionName, KieSessionConfiguration conf);

    /**
     * Returns the ClassLoader used by this KieContainer
     */
    ClassLoader getClassLoader();
}
