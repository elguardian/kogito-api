package org.drools.knowledge.definitions.process;

public interface Process {
	
    String getId();
    
    String getName();
    
    String getVersion();
    
    String getPackageName();
    
    String getType();
    
    Object getMetaData(String name);

}
