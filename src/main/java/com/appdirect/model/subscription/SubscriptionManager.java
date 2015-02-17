package com.appdirect.model.subscription;

import com.appdirect.controller.rest.payloads.event.generated.EventType;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.transform.Source;
import java.util.*;

/**
 * Created by NENE on 2015-02-14.
 */
@Component
public class SubscriptionManager {

    final static Logger logger = LoggerFactory.getLogger(SubscriptionManager.class);

    private Map<String, Subscription> activeSubscriptions = Collections.synchronizedMap(new HashMap<String, Subscription>());

    @Autowired
    private SubscriptionFactory subscriptionFactory;

    public String createSubscription(String event) {
        try {
            Subscription newSubscription = subscriptionFactory.buildSubscription(event);
            activeSubscriptions.put(newSubscription.getId(), newSubscription);
            LoggerUtils.logDebug(logger, "Subscription %s added", newSubscription.getId());
            return newSubscription.getId();
        } catch (Exception e) {
            LoggerUtils.logError(logger, e, "Error creating subcription for event");
            return null;
        }
    }

    public boolean updateSubscription(String event) {
        Subscription subscriptionReceived = subscriptionFactory.retrieveSubscription(event);

        if (!containsSubscriptions(subscriptionReceived)) return false;

        Subscription subscriptionToUpdate = activeSubscriptions.get(subscriptionReceived.getId());
        subscriptionToUpdate.setPricing(subscriptionReceived.getPricing());
        subscriptionToUpdate.setSubscriptionType(subscriptionReceived.getSubscriptionType());
        subscriptionToUpdate.setSubscriptionStatus(subscriptionReceived.getSubscriptionStatus());
        return true;
    }

    public boolean deleteSubscription(String event) {
        Subscription subscriptionReceived = subscriptionFactory.retrieveSubscription(event);

        if (!containsSubscriptions(subscriptionReceived)) return false;

        activeSubscriptions.remove(subscriptionReceived.getId());
        return true;
    }


    public void updateStatusSubscriptions(String event) {
        updateSubscription(event);
    }


    private boolean containsSubscriptions(Subscription subscriptionReceived) {
        if (activeSubscriptions.containsKey(subscriptionReceived.getId())) {
            return true;
        } else {
            LoggerUtils.logDebug(logger, "Subscription %s not found", subscriptionReceived.getId());
            return false;
        }

    }

    public Collection<Subscription> getSubscriptions(){
        return new ArrayList<Subscription>(activeSubscriptions.values());
    }
}
