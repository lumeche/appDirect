package com.appdirect.model.utils;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by NENE on 2015-02-14.
 */
public class OAuthUtil {

    public static void signRequest(HttpURLConnection request){
        OAuthConsumer consumer = new DefaultOAuthConsumer("Dummy", "secret");
        try {
            consumer.sign(request);
            request.connect();
        } catch (OAuthMessageSignerException | OAuthExpectationFailedException | OAuthCommunicationException | IOException ex) {
            ex.printStackTrace();
        }

    }
}
