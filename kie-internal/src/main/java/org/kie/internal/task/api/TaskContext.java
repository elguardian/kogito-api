package org.kie.internal.task.api;

import org.kie.api.task.UserGroupCallback;
import org.kie.internal.command.Context;

public interface TaskContext extends Context {

	TaskPersistenceContext getPersistenceContext();
	
	void setPersistenceContext(TaskPersistenceContext context);
	
	UserGroupCallback getUserGroupCallback();

}
