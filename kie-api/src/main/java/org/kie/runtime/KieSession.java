package org.kie.runtime;

import org.kie.runtime.process.StatefulProcessSession;
import org.kie.runtime.rule.StatefulRuleSession;

/**
 * KieSession is the most common way to interact with the engine. A KieSession
 * allows the application to establish an iterative conversation with the engine, where the state of the
 * session is kept across invocations. The reasoning process may be triggered multiple times for the
 * same set of data. After the application finishes using the session, though, it <b>must</b> call the
 * <code>dispose()</code> method in order to free the resources and used memory.
 *
 * <p>
 * Simple example showing a KieSession executing rules for a given collection of java objects.
 * </p>
 * <pre>
 * KieServices kieServices = KieServices.Factory.get();
 * KieContainer kContainer = kieServices.getKieClasspathContainer();
 * KieSession kSession = kContainer.newKieSession();
 *
 * for( Object fact : facts ) {
 * kSession.insert( fact );
 * }
 * kSession.fireAllRules();
 * kSession.dispose();
 * </pre>
 *
 * <p>
 * Simple example showing a stateful session executing processes.
 * </p>
 * <pre>
 * KieSession kSession = kbase.newStatefulKnowledgeSession();
 * kSession.startProcess("com.sample.processid");
 * kSession.signalEvent("SomeEvent", null);
 * kSession.startProcess("com.sample.processid");
 * kSession.dispose();
 * </pre>
 *
 * <p>
 * KieSession support globals. Globals are used to pass information into the engine
 * (like data or service callbacks that can be used in your rules and processes), but they should not
 * be used to reason over. If you need to reason over your data, make sure you insert
 * it as a fact, not a global.</p>
 * <p>Globals are shared among ALL your rules and processes, so be especially careful of (and avoid
 * as much as possible) mutable globals. Also, it is a good practice to set your globals before
 * inserting your facts or starting your processes. Rules engines evaluate rules at fact insertion
 * time, and so, if you are using a global to constraint a fact pattern, and the global is not set,
 * you may receive a <code>NullPointerException</code>. </p>
 * <p>Globals can be resolved in two ways. The KieSession supports getGlobals() which
 * returns the internal Globals, which itself can take a delegate. Calling of setGlobal(String, Object)
 * will set the global on an internal Collection. Identifiers in this internal
 * Collection will have priority over the externally supplied Globals delegate. If an identifier cannot be found in
 * the internal Collection, it will then check the externally supplied Globals delegate, if one has been set.
 * </p>
 *
 * <p>Code snippet for setting a global:</p>
 * <pre>
 * StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
 * ksession.setGlobal( "hbnSession", hibernateSession ); // sets a global hibernate session, that can be used for DB interactions in the rules.
 * for( Object fact : facts ) {
 * ksession.insert( fact );
 * }
 * ksession.fireAllRules(); // this will now execute and will be able to resolve the "hbnSession" identifier.
 * ksession.dispose();
 * </pre>
 *
 * <p>
 * Like StatelessKieSession this also implements CommandExecutor which can be used to script a KieSession. See CommandExecutor
 * for more details.
 * </p>
 *
 * @see org.drools.runtime.Globals
 */
public interface KieSession
        extends
        StatefulRuleSession,
        StatefulProcessSession,
        CommandExecutor,
        KieRuntime {

    int getId();

    /**
     * Releases all the current session resources, setting up the session for garbage collection.
     * This method <b>must</b> always be called after finishing using the session, or the engine
     * will not free the memory used by the session.
     */
    void dispose();
    
    
    /**
     * Destroys session permanently. In case of session state being persisted in data store
     * it will be removed from it otherwise it falls back to default dispose() method. 
     */
    void destroy();
}
