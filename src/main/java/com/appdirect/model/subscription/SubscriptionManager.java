package com.appdirect.model.subscription;

import com.appdirect.exceptions.SubscriptionException;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by NENE on 2015-02-14.
 */
@Component
public class SubscriptionManager {

    private final static Logger logger = LoggerFactory.getLogger(SubscriptionManager.class);

    public static final String ACTIVE="ACTIVE";
    public static final String DELETED="DELETED";

    private Map<String, Subscription> activeSubscriptions = Collections.synchronizedMap(new HashMap<String, Subscription>());

    @Autowired
    private ISubscriptionFactory subscriptionFactory;

    public String createSubscription(String event) {
        try {
            Subscription newSubscription = subscriptionFactory.buildNewSubscription(event);
            activeSubscriptions.put(newSubscription.getId(), newSubscription);
            LoggerUtils.logDebug(logger, "Subscription %s added", newSubscription.getId());
            return newSubscription.getId();
        } catch (Exception e) {
            LoggerUtils.logError(logger, e, "Error creating subcription for event");
            return null;
        }
    }

    public boolean updateSubscription(String event) {
        Subscription subscriptionReceived = subscriptionFactory.retrieveExistingSubscription(event);

        if (!containsSubscriptions(subscriptionReceived.getId())) return false;

        Subscription subscriptionToUpdate = activeSubscriptions.get(subscriptionReceived.getId());
        subscriptionToUpdate.setPricing(subscriptionReceived.getPricing());
        subscriptionToUpdate.setSubscriptionType(subscriptionReceived.getSubscriptionType());
        subscriptionToUpdate.setSubscriptionStatus(subscriptionReceived.getSubscriptionStatus());
        return true;
    }

    public boolean deleteSubscription(String event) {
        String subscriptionId = subscriptionFactory.getSubscriptionId(event);
        if (!containsSubscriptions(subscriptionId)) return false;
        activeSubscriptions.get(subscriptionId).setSubscriptionStatus(DELETED);
        activeSubscriptions.remove(subscriptionId);
        return true;
    }


    public boolean updateStatusSubscriptions(String event) {
        String subscriptionId= subscriptionFactory.getSubscriptionId(event);
        String subscriptionStatus=subscriptionFactory.getNotice(event);

        if (!containsSubscriptions(subscriptionId)) return false;
        Subscription subscriptionToUpdate=activeSubscriptions.get(subscriptionId);
        subscriptionToUpdate.setSubscriptionStatus(subscriptionStatus);
        return true;
    }

    private boolean containsSubscriptions(String subscriptionId) {
        if(activeSubscriptions.containsKey(subscriptionId)){
            return true;
        }else{
            LoggerUtils.logDebug(logger, "Subscription %s not found", subscriptionId);
            return false;
        }
    }

    public Subscription findSubscription(String event){
        String subscriptionId=subscriptionFactory.getSubscriptionId(event);
        if(subscriptionId==null || !containsSubscriptions(subscriptionId)){
            throw new SubscriptionException(String.format("Subscription Id %s didn't found",subscriptionId));
        }
        return activeSubscriptions.get(subscriptionId);
    }


    public Collection<Subscription> getSubscriptions(){
        return new ArrayList<Subscription>(activeSubscriptions.values());
    }
}
