package com.appdirect.model.subscription;

import com.appdirect.controller.rest.payloads.event.generated.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by NENE on 2015-02-14.
 */
@Component
public class SubscriptionManager {

    final static Logger logger = LoggerFactory.getLogger(SubscriptionManager.class);

    private Map<String,Subscription> activeSubscriptions=new HashMap<String,Subscription>();

    @Autowired
    private SubscriptionFactory subscriptionFactory;
    public boolean createSubscription(EventType event){
        Subscription newSubscription=subscriptionFactory.buildSubscription(event);
        return false;
    }

    public boolean updateSubscription(EventType event){

        return false;
    }

    public boolean deleteSubscription(EventType event){

        return false;
    }


    public void updateStatusSubscriptions(EventType event) {
    }



}
