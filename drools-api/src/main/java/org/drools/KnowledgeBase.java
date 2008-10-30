package org.drools;

import java.util.Collection;

import org.drools.event.knowledgebase.KnowledgeBaseEventManager;
import org.drools.knowledge.definitions.KnowledgePackage;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;

public interface KnowledgeBase extends KnowledgeBaseEventManager {    
    void addKnowledgePackage(KnowledgePackage knowledgePackage);
    
    void addKnowledgePackages(Collection<KnowledgePackage> knowledgePackage);
    
    Collection<KnowledgePackage> getKnowledgePackages();
    
    StatefulKnowledgeSession newStatefulKnowledgeSession();
    
    void removeKnowledgePackage(String packageName);

    void removeRule(String packageName,
                    String ruleName);

    StatefulKnowledgeSession newStatefulSession(KnowledgeSessionConfiguration conf);
    
    
}
