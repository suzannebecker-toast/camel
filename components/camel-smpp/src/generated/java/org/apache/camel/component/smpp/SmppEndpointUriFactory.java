/* Generated by camel build tools - do NOT edit this file! */
package org.apache.camel.component.smpp;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.camel.spi.EndpointUriFactory;

/**
 * Generated by camel build tools - do NOT edit this file!
 */
public class SmppEndpointUriFactory extends org.apache.camel.support.component.EndpointUriFactorySupport implements EndpointUriFactory {

    private static final String BASE = ":host:port";
    private static final String[] SCHEMES = new String[]{"smpp", "smpps"};

    private static final Set<String> PROPERTY_NAMES;
    private static final Set<String> SECRET_PROPERTY_NAMES;
    private static final Set<String> MULTI_VALUE_PREFIXES;
    static {
        Set<String> props = new HashSet<>(43);
        props.add("serviceType");
        props.add("lazySessionCreation");
        props.add("initialReconnectDelay");
        props.add("destAddrTon");
        props.add("httpProxyHost");
        props.add("numberingPlanIndicator");
        props.add("alphabet");
        props.add("destAddrNpi");
        props.add("password");
        props.add("bridgeErrorHandler");
        props.add("host");
        props.add("systemType");
        props.add("sourceAddr");
        props.add("sourceAddrNpi");
        props.add("systemId");
        props.add("pduProcessorDegree");
        props.add("addressRange");
        props.add("exchangePattern");
        props.add("singleDLR");
        props.add("sessionStateListener");
        props.add("usingSSL");
        props.add("registeredDelivery");
        props.add("httpProxyPort");
        props.add("reconnectDelay");
        props.add("encoding");
        props.add("httpProxyPassword");
        props.add("pduProcessorQueueCapacity");
        props.add("transactionTimer");
        props.add("splittingPolicy");
        props.add("lazyStartProducer");
        props.add("protocolId");
        props.add("proxyHeaders");
        props.add("httpProxyUsername");
        props.add("typeOfNumber");
        props.add("port");
        props.add("dataCoding");
        props.add("enquireLinkTimer");
        props.add("destAddr");
        props.add("replaceIfPresentFlag");
        props.add("priorityFlag");
        props.add("maxReconnect");
        props.add("exceptionHandler");
        props.add("sourceAddrTon");
        PROPERTY_NAMES = Collections.unmodifiableSet(props);
        Set<String> secretProps = new HashSet<>(2);
        secretProps.add("password");
        secretProps.add("systemId");
        SECRET_PROPERTY_NAMES = Collections.unmodifiableSet(secretProps);
        MULTI_VALUE_PREFIXES = Collections.emptySet();
    }

    @Override
    public boolean isEnabled(String scheme) {
        for (String s : SCHEMES) {
            if (s.equals(scheme)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String buildUri(String scheme, Map<String, Object> properties, boolean encode) throws URISyntaxException {
        String syntax = scheme + BASE;
        String uri = syntax;

        Map<String, Object> copy = new HashMap<>(properties);

        uri = buildPathParameter(syntax, uri, "host", "localhost", false, copy);
        uri = buildPathParameter(syntax, uri, "port", "2775", false, copy);
        uri = buildQueryParameters(uri, copy, encode);
        return uri;
    }

    @Override
    public Set<String> propertyNames() {
        return PROPERTY_NAMES;
    }

    @Override
    public Set<String> secretPropertyNames() {
        return SECRET_PROPERTY_NAMES;
    }

    @Override
    public Set<String> multiValuePrefixes() {
        return MULTI_VALUE_PREFIXES;
    }

    @Override
    public boolean isLenientProperties() {
        return true;
    }
}

