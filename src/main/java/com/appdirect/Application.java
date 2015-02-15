package com.appdirect;

import com.appdirect.controller.rest.SubscriptionHandler;
import com.appdirect.controller.web.IsAlive;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * Created by NENE on 2015-02-12.
 */
@ComponentScan
@EnableAutoConfiguration
@ImportResource(value = {"app-direct-configuration.xml"})
public class Application {

    public static void main(String[] args) {
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }
        System.setProperty("server.port", webPort);
        SpringApplication.run(Application.class, args);
    }
}
