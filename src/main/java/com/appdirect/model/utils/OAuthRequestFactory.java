package com.appdirect.model.utils;

import com.appdirect.exceptions.OAuthException;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by NENE on 2015-02-14.
 */

public class OAuthRequestFactory extends SimpleClientHttpRequestFactory implements InitializingBean{

    final static Logger logger = LoggerFactory.getLogger(OAuthRequestFactory.class);
    @Value("${oauth.consumer.key}")
    private String key;
    @Value("${oauth.consumer.secret}")
    private String secret;

    private OAuthConsumer consumer;

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) {
        try {
            LoggerUtils.logDebug(logger,"About to sign with key: %s and secret(half): %s",key,secret.substring(0,secret.length()/2));
            consumer.sign(connection);
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException e) {
            throw new OAuthException("Error signing the connection", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        consumer = new DefaultOAuthConsumer(key, secret);
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

     String getKey() {
        return key;
    }
     String getSecret() {
        return secret;
    }
}
