package org.drools;

import java.util.Properties;

public interface KnowledgeBaseProvider {

    /**
     * Instantiate and return a new KnowledgeBaseConfiguration
     * 
     * @return
     *     the KnowledgeBaseConfiguration
     */
    public KnowledgeBaseConfiguration newKnowledgeBaseConfiguration();

    /**
     * Instantiate and return a new KnowledgeBaseConfiguration
     * 
     * @param properties
     *     Properties file to process, can be null;
     * @param classLoader
     *     Provided ClassLoader, can be null and then ClassLoader defaults to Thread.currentThread().getContextClassLoader()
     * @return
     *     The KnowledgeBaseConfiguration
     */
    public KnowledgeBaseConfiguration newKnowledgeBaseConfiguration(Properties properties,
                                                                    ClassLoader classLoader);

    /**
     * Instantiate and return a KnowledgeBase using a default KnowledgeBaseConfiguration
     * 
     * @return
     *      The KnowledgeBase
     */
    KnowledgeBase newKnowledgeBase();

    /**
     * Instantiate and return a KnowledgeBase using the given KnowledgeBaseConfiguration
     * 
     * @param conf
     *     The KnowledgeBaseConfiguration to be used
     * @return
     *     The KnowledgeBase
     */
    KnowledgeBase newKnowledgeBase(KnowledgeBaseConfiguration conf);

}
