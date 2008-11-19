package org.drools.runtime.rule;

import org.drools.definition.rule.Rule;

public interface PropagationContext {
    public static final int ASSERTION     = 0;
    public static final int RETRACTION    = 1;
    public static final int MODIFICATION  = 2;
    public static final int RULE_ADDITION = 3;
    public static final int RULE_REMOVAL  = 4;
    public static final int EXPIRATION    = 5;

    public long getPropagationNumber();

    public Rule getRule();

    public FactHandle getFactHandle();

    public int getType();
}
