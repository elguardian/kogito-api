package org.drools.runtime;

/**
 * ObjectFilter is used with WorkingMemories to filter out instances during Iteration
 *
 */
public interface ObjectFilter {

    /**
     * Returning true means the Iterator accepts, and thus returns, the current Object.
     * @param object
     * @return
     */
    boolean accept(Object object);
}
