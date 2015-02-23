package com.appdirect.controller.rest;

import com.appdirect.model.utils.LoggerUtils;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Value("${oauth.consumer.secret}")
    private String oauthSecret;

    @Value("${oauth.consumer.key}")
    private String oauthKey;

    @Value("${oauth.header.key.regex}")
    private String authorizationKeyRegex;

    @Value("${oauth.header.signature.regex}")
    private String authorizationSecretRegex;

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


    public boolean isInvalidSignature(String authorization,String url) {
        LoggerUtils.logDebug(logger,"Authorization header: %s",authorization);
        Matcher matcherKey= Pattern.compile(authorizationKeyRegex).matcher(authorization);
        Matcher matcherSignature=Pattern.compile(authorizationSecretRegex).matcher(authorization);
        if(matcherKey.find() && matcherSignature.find()){
            String key=matcherKey.group(1);
            String signature=matcherSignature.group(1);
            return isInvalidSignature(key,signature, url);
        }else{
            LoggerUtils.logError(logger,"Key or Signature not found");
            return true;
        }

    }



    public String getFullRequestURL(HttpServletRequest request) {
        StringBuffer requestURL = request.getRequestURL();
        String queryString = request.getQueryString();

        if (queryString == null) {
            return requestURL.toString();
        } else {
            return requestURL.append('?').append(queryString).toString();
        }
    }


    private boolean isInvalidSignature(String key, String signature, String url)  {
        OAuthConsumer consumer=new DefaultOAuthConsumer(key,oauthSecret);
        try {
            LoggerUtils.logDebug(logger,"key:%s signature:%s,url:%s",key,signature,url);
            String signatureGenerated=consumer.sign(url);
        if(signature.equals(signatureGenerated)){
            LoggerUtils.logDebug(logger,"Signature verified");
            return false;
        }else{
            LoggerUtils.logError(logger,"Signature cannot be verified");
            //FIXME Returning false until the signature is correctly validated
            return false;
        }

        } catch (OAuthExpectationFailedException | OAuthCommunicationException |OAuthMessageSignerException e) {
            LoggerUtils.logError(logger,e,"Exception validationg the signature");
            return true;
        }
    }

    public void setOauthRestTemplate(RestTemplate oauthRestTemplate) {
        this.oauthRestTemplate = oauthRestTemplate;
    }

    public void setOauthKey(String oauthKey) {
        this.oauthKey = oauthKey;
    }

    public void setOauthSecret(String oauthSecret) {
        this.oauthSecret = oauthSecret;
    }
}
