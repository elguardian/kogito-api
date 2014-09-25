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

package org.kie.internal.utils;

import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.conf.KieBaseOption;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;

import java.io.InputStream;

import static org.kie.api.io.ResourceType.determineResourceType;

public class KieHelper {

    public final KieServices ks = KieServices.Factory.get();

    public final KieFileSystem kfs = ks.newKieFileSystem();

    private int counter = 0;

    public KieBase build( KieBaseConfiguration kieBaseConf ) {
        KieContainer kieContainer = getKieContainer();
        return kieContainer.newKieBase( kieBaseConf );
    }

    public KieBase build(KieBaseOption... options) {
        KieContainer kieContainer = getKieContainer();
        if (options == null || options.length == 0) {
            return kieContainer.getKieBase();
        }
        KieBaseConfiguration kieBaseConf = ks.newKieBaseConfiguration();
        for (KieBaseOption option : options) {
            kieBaseConf.setOption(option);
        }

        return kieContainer.newKieBase(kieBaseConf);
    }

    protected KieContainer getKieContainer() {
        KieBuilder kieBuilder = ks.newKieBuilder( kfs ).buildAll();
        Results results = kieBuilder.getResults();
        if (results.hasMessages(Message.Level.ERROR)) {
            throw new RuntimeException(results.getMessages().toString());
        }
        KieContainer kieContainer = ks.newKieContainer(ks.getRepository().getDefaultReleaseId());
        return kieContainer;
    }

    public Results verify() {
        KieBuilder kieBuilder = ks.newKieBuilder( kfs ).buildAll();
        return kieBuilder.getResults();
    }

    public KieHelper addContent(String content, ResourceType type) {
        kfs.write(generateResourceName(type), content);
        return this;
    }

    public KieHelper addFromClassPath(String name) {
        return addFromClassPath(name, null);
    }

    public KieHelper addFromClassPath(String name, String encoding) {
        InputStream input = getClass().getResourceAsStream(name);
        if (input == null) {
            throw new IllegalArgumentException("The file (" + name + ") does not exist as a classpath resource.");
        }
        ResourceType type = determineResourceType(name);
        kfs.write(generateResourceName(type), ks.getResources().newInputStreamResource(input, encoding));
        return this;
    }

    public KieHelper addResource(Resource resource) {
        kfs.write(resource);
        return this;
    }

    public KieHelper addResource(Resource resource, ResourceType type) {
        if (resource.getSourcePath() == null && resource.getTargetPath() == null) {
            resource.setSourcePath(generateResourceName(type));
        }
        return addResource(resource);
    }

    private String generateResourceName(ResourceType type) {
        return "src/main/resources/file" + counter++ + "." + type.getDefaultExtension();
    }
}
