package com.appdirect.model.subscription;

import com.appdirect.controller.rest.payloads.event.generated.EventType;
import com.appdirect.model.utils.LoggerUtils;
import com.appdirect.model.utils.NodeMapperBuilder;
import org.omg.PortableInterceptor.ACTIVE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.xml.xpath.NodeMapper;
import org.springframework.xml.xpath.XPathOperations;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import javax.xml.transform.Source;
import java.util.UUID;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@Component
public class SubscriptionFactory {
    final static Logger logger = LoggerFactory.getLogger(SubscriptionFactory.class);

    private static final String ACTIVE="ACTIVE";

    @Value("${appdirect.events.editioncode.xpath}")
    private String editionCodeXPath;

    @Value("${appdirect.events.pricing.xpath}")
    private String pricingXPath;

    @Autowired
    private XPathOperations xpathTemplate;

    public Subscription buildSubscription(Source event) {
        Subscription subscription=new Subscription();

        String pricing=xpathTemplate.evaluateAsString(pricingXPath,event);
        String subscriptionType=xpathTemplate.evaluateAsString(editionCodeXPath,event);

        subscription.setId(UUID.randomUUID().toString());
        subscription.setPricing(pricing);
        subscription.setSubscriptionType(subscriptionType);
        subscription.setSubscriptionStatus(ACTIVE);
        LoggerUtils.logDebug(logger,"subscription %s created",subscription.toString());
        return subscription;
    }

    public void setXpathTemplate(XPathOperations xpathTemplate) {
        this.xpathTemplate = xpathTemplate;
    }

    public void setEditionCodeXPath(String editionCodeXPath) {
        this.editionCodeXPath = editionCodeXPath;
    }

    public void setPricingXPath(String pricingXPath) {
        this.pricingXPath = pricingXPath;
    }
}
