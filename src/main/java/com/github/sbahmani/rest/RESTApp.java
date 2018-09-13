package com.github.sbahmani.rest;

import org.glassfish.jersey.server.ResourceConfig;

/**
 *
 * @author sjb
 */
public class RESTApp extends ResourceConfig {

    public RESTApp() {
        packages("com.github.sbahmani.rest");        
    }

}
