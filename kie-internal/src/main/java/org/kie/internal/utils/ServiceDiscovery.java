package org.kie.internal.utils;

import org.kie.internal.assembler.KieAssemblerService;
import org.kie.internal.assembler.KieAssemblers;
import org.kie.internal.runtime.KieRuntimeService;
import org.kie.internal.runtime.KieRuntimes;
import org.kie.internal.runtime.beliefs.KieBeliefService;
import org.kie.internal.runtime.beliefs.KieBeliefs;
import org.kie.internal.utils.ServiceRegistryImpl.ReturnInstance;
import org.kie.internal.weaver.KieWeaverService;
import org.kie.internal.weaver.KieWeavers;
import org.mvel2.MVEL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

public class ServiceDiscovery {

    private static final Logger log = LoggerFactory.getLogger(ServiceDiscovery.class);

//    public final String fileName = "kie.conf";
//    public final String path =  "META-INF/" + fileName;
//
//    private static final ServiceDiscovery INSTANCE = new ServiceDiscovery();
//
//    public static ServiceDiscovery getInstance() {
//        return INSTANCE;
//    }
//
//    private  ServiceDiscovery() {
//    }

    public static void discoverFactories(String path, ServiceRegistry serviceRegistry) {
        ClassLoader classLoader = getClassLoader();

        List list = new ArrayList();

        final Enumeration<URL> e;
        try {
            e = classLoader.getResources( path );
        } catch ( IOException exc ) {
            log.error( "Unable to find and build kie service for url={}\n message={} ", path, exc.getMessage() );
            return;
        }

        // iterate urls, then for each url split the service key and attempt to register each service
        while ( e.hasMoreElements() ) {
            URL url = e.nextElement();
            java.io.InputStream is = null;
            try {
                is = url.openStream();
                log.info( "Discovered kie.conf url={} ", url );
                processKieConf( is, serviceRegistry );
            } catch ( Exception exc ) {
                log.error( "Unable to build kie service url={} reason={}\n", url.toExternalForm(), exc.getMessage() );
            } finally {
                try {
                    if ( is != null ) {
                        is.close();
                    } else {
                        log.error( "Unable to build kie service url={}\n", url.toExternalForm() );
                    }
                } catch (IOException e1) {
                    log.warn( "Unable to close Stream for url={} reason={}", url, e1.getMessage() );
                }
            }
        }

        return;
    }

    public static void processKieConf(InputStream is, ServiceRegistry serviceRegistry) throws IOException {
        String conf = readFileAsString( new InputStreamReader( is ));
        processKieConf( conf, serviceRegistry );
    }

    public static void processKieConf(String conf, ServiceRegistry serviceRegistry) {
        Map map = ( Map ) MVEL.eval( conf );
        processKieConf(map, serviceRegistry);
    }

    public static void processKieConf(Map map, ServiceRegistry serviceRegistry) {
        processKieServices(map, serviceRegistry);

        processKieAssemblers(map, serviceRegistry);

        processKieWeavers(map, serviceRegistry);

        processKieBeliefs(map, serviceRegistry);

        processRuntimes(map, serviceRegistry);
    }


    private static void processRuntimes(Map map, ServiceRegistry serviceRegistry) {
        List<KieRuntimeService> runtimeList = ( List<KieRuntimeService> ) map.get( "runtimes" );
        if ( runtimeList != null && runtimeList.size() > 0 ) {
            KieRuntimes runtimes = (KieRuntimes) serviceRegistry.get(KieRuntimes.class);

            for ( KieRuntimeService runtime : runtimeList ) {
                log.info("Adding Runtime {} ", runtime.getServiceInterface().getName());
                runtimes.getRuntimes().put( runtime.getServiceInterface().getName(),
                                            runtime);
            }

        }
    }

    private static void processKieAssemblers(Map map, ServiceRegistry serviceRegistry) {
        List<KieAssemblerService> assemblerList = ( List<KieAssemblerService> ) map.get( "assemblers" );
        if ( assemblerList != null && assemblerList.size() > 0 ) {
            KieAssemblers assemblers = (KieAssemblers) serviceRegistry.get(KieAssemblers.class);
            for ( KieAssemblerService assemblerFactory : assemblerList ) {
                log.info( "Adding Assembler {} ", assemblerFactory.getClass().getName() );
                assemblers.getAssemblers().put(assemblerFactory.getResourceType(),
                                               assemblerFactory);
            }
        }
    }

    private static void processKieWeavers(Map map, ServiceRegistry serviceRegistry) {
        List<KieWeaverService> weaverList = ( List<KieWeaverService> ) map.get( "weavers" );
        if ( weaverList != null && weaverList.size() > 0 ) {
            KieWeavers weavers = (KieWeavers) serviceRegistry.get(KieWeavers.class);
            for ( KieWeaverService weaver : weaverList ) {
                log.info("Adding Weaver {} ", weavers.getClass().getName());
                weavers.getWeavers().put( weaver.getResourceType(),
                                          weaver );
            }
        }
    }

    private static void processKieBeliefs(Map map, ServiceRegistry serviceRegistry) {
        List<KieBeliefService> beliefsList = ( List<KieBeliefService> ) map.get( "beliefs" );
        if ( beliefsList != null && beliefsList.size() > 0 ) {
            KieBeliefs beliefs = (KieBeliefs) serviceRegistry.get(KieBeliefs.class);
            for ( KieBeliefService belief : beliefsList ) {
                log.info("Adding Belief {} ", beliefs.getClass().getName());
                beliefs.getBeliefs().put( belief.getBeliefType(),
                                          belief );
            }
        }
    }

    private static void processKieServices(Map map, ServiceRegistry serviceRegistry) {List<KieService> servicesList = ( List ) map.get( "services" );
        if ( servicesList != null && servicesList.size() > 0 ) {
            for ( KieService service : servicesList ) {
                log.info( "Adding Service {} ", service.getClass().getName() );
                serviceRegistry.registerLocator(service.getServiceInterface(), new ReturnInstance(service));
            }
        }
    }

    public static String readFileAsString(Reader reader) {
        try {
            StringBuilder fileData = new StringBuilder( 1000 );
            char[] buf = new char[1024];
            int numRead;
            while ( (numRead = reader.read( buf )) != -1 ) {
                String readData = String.valueOf( buf,
                                                  0,
                                                  numRead );
                fileData.append( readData );
                buf = new char[1024];
            }
            reader.close();
            return fileData.toString();
        } catch ( IOException e ) {
            throw new RuntimeException( e );
        }
    }

    public static ClassLoader getClassLoader() {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = ClassLoader.getSystemClassLoader();
        }
        if (cl == null) {
            cl = ClassLoaderUtil.class.getClassLoader();
        }
        return cl;
    }
}

