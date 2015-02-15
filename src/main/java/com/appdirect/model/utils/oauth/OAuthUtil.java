package com.appdirect.model.utils.oauth;

import com.appdirect.model.utils.LoggerUtils;
import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;

/**
 * Created by NENE on 2015-02-14.
 */
@Component
public class OAuthUtil {

    final static Logger logger = LoggerFactory.getLogger(OAuthUtil.class);

    @Value("${oauth.consumer.key}")
    private String key;
    @Value("${oauth.consumer.secret}")
    private String secret;

    public void signRequest(HttpURLConnection request){
        LoggerUtils.logDebug(logger, "key:%s half secret :P:%s", key, secret.substring(0, secret.length() / 2));
        OAuthConsumer consumer = new DefaultOAuthConsumer(key, secret);
        try {
            consumer.sign(request);
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException ex) {
            ex.printStackTrace();
        }

    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
