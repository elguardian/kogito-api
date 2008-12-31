package org.drools.runtime.pipeline;

import javax.xml.bind.Unmarshaller;

import org.drools.ProviderInitializationException;
import org.milyn.Smooks;

import com.thoughtworks.xstream.XStream;

public class PipelineFactory {

    private static CorePipelineProvider   corePipelineProvider;

    private static JaxbPipelineProvider   jaxbPipelineProvider;

    private static SmooksPipelineProvider smooksPipelineProvider;
    
    private static XStreamPipelineProvider xstreamPipelineProvider;

    public static Transformer newSmooksTransformer(Smooks smooks,
                                                   String rootId) {
        return getSmooksPipelineProvider().newSmooksTransformer( smooks,
                                                                 rootId );
    }

    public static Transformer newJaxbTransformer(Unmarshaller unmarshaller) {
        return getJaxbPipelineProvider().newJaxbTransformer( unmarshaller );
    }
    
    public static Transformer newXStreamTransformer(XStream xstream) {
    	 return getXStreamPipelineProvider().newXStreamTransformer( xstream );
    }

    public static Expression newMvelExpression(String expression) {
        return getCorePipelineProvider().newMvelExpression( expression );
    }

    public static Splitter newIterateSplitter() {
        return getCorePipelineProvider().newIterateSplitter();
    }

    public static Adapter newEntryPointReceiverAdapter() {
        return getCorePipelineProvider().newEntryPointReceiverAdapter();
    }

    public static Adapter newStatelessKnowledgeSessionReceiverAdapter() {
        return getCorePipelineProvider().newStatelessKnowledgeSessionReceiverAdapter();
    }
    
    private static synchronized void setCorePipelineProvider(CorePipelineProvider provider) {
        PipelineFactory.corePipelineProvider = provider;
    }

    private static synchronized CorePipelineProvider getCorePipelineProvider() {
        if ( corePipelineProvider == null ) {
            loadCorePipelineProvider();
        }
        return corePipelineProvider;
    }

    private static void loadCorePipelineProvider() {
        try {
            Class<CorePipelineProvider> cls = (Class<CorePipelineProvider>) Class.forName( "org.drools.runtime.pipeline.impl.CorePipelineProviderImpl" );
            setCorePipelineProvider( cls.newInstance() );
        } catch ( Exception e2 ) {
            throw new ProviderInitializationException( "org.drools.runtime.pipeline.impl.CorePipelineProviderImpl could not be set.",
                                                       e2 );
        }
    }   
    
    private static synchronized void setJaxbPipelineProvider(JaxbPipelineProvider provider) {
        PipelineFactory.jaxbPipelineProvider = provider;
    }

    private static synchronized JaxbPipelineProvider getJaxbPipelineProvider() {
        if ( jaxbPipelineProvider == null ) {
            loadJaxbPipelineProvider();
        }
        return jaxbPipelineProvider;
    }

    private static void loadJaxbPipelineProvider() {
        try {
            Class<JaxbPipelineProvider> cls = (Class<JaxbPipelineProvider>) Class.forName( "org.drools.runtime.pipeline.impl.JaxbTransformer$JaxbPipelineProviderImpl" );
            setJaxbPipelineProvider( cls.newInstance() );
        } catch ( Exception e2 ) {
            throw new ProviderInitializationException( "Provider org.drools.runtime.pipeline.impl.JaxbTransformer$JaxbPipelineProviderImpl could not be set.",
                                                       e2 );
        }
    } 
    
    private static synchronized void setSmooksPipelineProvider(SmooksPipelineProvider provider) {
        PipelineFactory.smooksPipelineProvider = provider;
    }

    private static synchronized SmooksPipelineProvider getSmooksPipelineProvider() {
        if ( smooksPipelineProvider == null ) {
            loadSmooksPipelineProvider();
        }
        return smooksPipelineProvider;
    }
    
    private static void loadSmooksPipelineProvider() {
        try {
            Class<SmooksPipelineProvider> cls = (Class<SmooksPipelineProvider>) Class.forName( "org.drools.runtime.pipeline.impl.SmooksTransformer$SmooksPipelineProviderImpl" );
            setSmooksPipelineProvider( cls.newInstance() );
        } catch ( Exception e2 ) {
            throw new ProviderInitializationException( "Provider org.drools.runtime.pipeline.impl.SmooksTransformer$SmooksPipelineProviderImpl could not be set.",
                                                       e2 );
        }
    }      
    
    private static synchronized void setXStreamPipelineProvider(XStreamPipelineProvider provider) {
        PipelineFactory.xstreamPipelineProvider = provider;
    }

    private static synchronized XStreamPipelineProvider getXStreamPipelineProvider() {
        if ( xstreamPipelineProvider == null ) {
            loadXStreamPipelineProvider();
        }
        return xstreamPipelineProvider;
    }

    private static void loadXStreamPipelineProvider() {
        try {
            Class<XStreamPipelineProvider> cls = (Class<XStreamPipelineProvider>) Class.forName( "org.drools.runtime.pipeline.impl.XStreamTransformer$XStreamPipelineProviderImpl" );
            setXStreamPipelineProvider( cls.newInstance() );
        } catch ( Exception e2 ) {
            throw new ProviderInitializationException( "Provider org.drools.runtime.pipeline.impl.XStreamTransformer$XStreamPipelineProviderImpl could not be set.",
                                                       e2 );
        }
    }      

}
