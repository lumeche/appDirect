package com.appdirect.controller.rest;

import com.appdirect.model.utils.LoggerUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * Created by Luis Tobon on 2015-02-15.
 */

@Component
class HandlerDelegate {

    final static Logger logger = LoggerFactory.getLogger(HandlerDelegate.class);
    private static final CharSequence DUMMY = "dummy";

    @Autowired
    private RestTemplate oauthRestTemplate;

    @Value(value = "${appdirect.events.url}")
    private String appDirectEventsURL;

    public String getEventInfo(String token) {
        LoggerUtils.logDebug(logger, "About to send GET request to %s with token %s", appDirectEventsURL, token);
        ResponseEntity<String> response = sendGetRequest(token);
        String event=response.getBody();
        LoggerUtils.logDebug(logger,"Event  returned %s",event.toString());
        return event;
    }

    public ResponseEntity<String> sendGetRequest(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML));
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        return oauthRestTemplate.exchange(appDirectEventsURL, HttpMethod.GET,entity,String.class,token);
    }

    public boolean isDummyRequest(String token) {
        return StringUtils.containsIgnoreCase(token, DUMMY);
    }


    public ResponseEntity  buildForbiddenHTTPResponse() {
        return new ResponseEntity(HttpStatus.FORBIDDEN);
    }
    public ResponseEntity  buildHTTPResponse(Object response) {
        return new ResponseEntity(response, HttpStatus.OK);
    }


    public boolean isInvalidSignature(String authorization) {
        LoggerUtils.logDebug(logger,"Authorization header: %s",authorization);
        return false;
    }

    public void setOauthRestTemplate(RestTemplate oauthRestTemplate) {
        this.oauthRestTemplate = oauthRestTemplate;
    }
}
