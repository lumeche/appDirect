package com.appdirect.model.subscription;

import com.appdirect.model.EventManager;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@Component
public class SubscriptionFactory implements ISubscriptionFactory {
    final static Logger logger = LoggerFactory.getLogger(SubscriptionFactory.class);

    @Autowired
    private EventManager eventManager;

    @Value("${appdirect.events.editioncode.xpath}")
    private String editionCodeXPath;

    @Value("${appdirect.events.pricing.xpath}")
    private String pricingXPath;

    @Value("${appdirect.evetns.account.identifier}")
    private String accountIDXPath;
    @Value("${appdirect.events.account.status}")
    private String statusXPath;

    @Override
    public Subscription buildNewSubscription(String event) {
        String status=eventManager.getXpath(event,editionCodeXPath);
        return getSubscriptionWithGivenIdAndStatus(event, UUID.randomUUID().toString(),status);
    }

    @Override
    public Subscription retrieveExistingSubscription(String event){
        String id = getSubscriptionId(event);
        String status=eventManager.getXpath(event,statusXPath);
        LoggerUtils.logInfo(logger,"subscription %s status[%s] received",id,status);
        return getSubscriptionWithGivenIdAndStatus(event, id,status);
    }

    @Override
    public String getSubscriptionId(String event) {
        return eventManager.getXpath(event,accountIDXPath);
    }

    @Override
    public String getNotice(String event){
        return eventManager.getXpath(event, statusXPath);
    }


    private Subscription getSubscriptionWithGivenIdAndStatus(String event, String id,String status) {
        Subscription subscription=new Subscription();

        String pricing = eventManager.getXpath(event,pricingXPath);
        String subscriptionType=eventManager.getXpath(event,editionCodeXPath);

        subscription.setId(id);
        subscription.setPricing(pricing);
        subscription.setSubscriptionType(subscriptionType);
        subscription.setSubscriptionStatus(status);
        LoggerUtils.logDebug(logger, "subscription %s created", subscription.toString());
        return subscription;
    }



    public void setEditionCodeXPath(String editionCodeXPath) {
        this.editionCodeXPath = editionCodeXPath;
    }

    public void setPricingXPath(String pricingXPath) {
        this.pricingXPath = pricingXPath;
    }

    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }
}
