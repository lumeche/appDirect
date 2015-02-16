package com.appdirect.model.subscription;

import com.appdirect.controller.rest.payloads.event.generated.EventType;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.transform.Source;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by NENE on 2015-02-14.
 */
@Component
public class SubscriptionManager {

    final static Logger logger = LoggerFactory.getLogger(SubscriptionManager.class);

    private Map<String,Subscription> activeSubscriptions= Collections.synchronizedMap(new HashMap<String,Subscription>());

    @Autowired
    private SubscriptionFactory subscriptionFactory;
    public String createSubscription(Source event){
        try {
            Subscription newSubscription=subscriptionFactory.buildSubscription(event);
            activeSubscriptions.put(newSubscription.getId(),newSubscription);
            LoggerUtils.logDebug(logger,"Subscription %s added",newSubscription.getId());
            return newSubscription.getId();
        }catch (Exception e){
            LoggerUtils.logError(logger,e,"Error creating subcription for event");
            return null;
        }
    }

    public boolean updateSubscription(Source event){

        return false;
    }

    public boolean deleteSubscription(Source event){

        return false;
    }


    public void updateStatusSubscriptions(Source event) {
    }



}
