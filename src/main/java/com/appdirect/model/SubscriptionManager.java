package com.appdirect.model;

import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Created by NENE on 2015-02-14.
 */
@Component
public class SubscriptionManager {

    final static Logger logger = LoggerFactory.getLogger(SubscriptionManager.class);

    @Autowired
    private RestTemplate oauthRestTemplate;
    @Value(value = "${appdirect.events.url}")
    private String appDirectEventsURL;

    public boolean createSubscription(String token){
        getEventInfo(token);
        return false;
    }

    public boolean updateSubscription(String token){
        getEventInfo(token);
        return false;
    }

    public boolean deleteSubscription(String token){
        getEventInfo(token);
        return false;
    }

    private void getEventInfo(String token) {
        LoggerUtils.logDebug(logger, "About to send GET request to %s with token $s", appDirectEventsURL, token);
        String subscriptionEvent=oauthRestTemplate.getForObject(appDirectEventsURL,String.class,token);
        LoggerUtils.logDebug(logger,"Event returned %s",subscriptionEvent);
    }


    public void setOauthRestTemplate(RestTemplate oauthRestTemplate) {
        this.oauthRestTemplate = oauthRestTemplate;
    }
}
