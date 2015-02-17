package com.appdirect.model.subscription;

import com.appdirect.model.EventManager;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.xml.xpath.XPathOperations;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import java.util.UUID;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@Component
public class SubscriptionFactory {
    final static Logger logger = LoggerFactory.getLogger(SubscriptionFactory.class);



    @Autowired
    private EventManager eventManager;

    @Value("${appdirect.events.editioncode.xpath}")
    private String editionCodeXPath;

    @Value("${appdirect.events.pricing.xpath}")
    private String pricingXPath;

    @Value("${appdirect.evetns.account.identifier}")
    private String accountIDXPath;
    @Value("${appdirect.events.account.notice}")
    private String noticeXPath;

    public Subscription buildSubscription(String event) {
        return getSubscriptionWithGivenId(event,UUID.randomUUID().toString());
    }

    public Subscription retrieveSubscription(String event){
        String id = getSubscriptionId(event);
        LoggerUtils.logInfo(logger,"subscription %s received",id);
        return getSubscriptionWithGivenId(event,id);
    }

    public String getSubscriptionId(String event) {
        return eventManager.getXpath(event,accountIDXPath);
    }

    public String getNotice(String event){
        return eventManager.getXpath(event,noticeXPath);
    }


    private Subscription getSubscriptionWithGivenId(String event,String id) {
        Subscription subscription=new Subscription();

        String pricing = eventManager.getXpath(event,pricingXPath);
        String subscriptionType=eventManager.getXpath(event,editionCodeXPath);

        subscription.setId(id);
        subscription.setPricing(pricing);
        subscription.setSubscriptionType(subscriptionType);
        subscription.setSubscriptionStatus(SubscriptionManager.ACTIVE);
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
