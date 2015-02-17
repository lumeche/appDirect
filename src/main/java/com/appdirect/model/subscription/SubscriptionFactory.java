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
import javax.xml.transform.stream.StreamSource;
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

    @Value("${appdirect.evetns.account.identifier}")
    private String accountIDXPath;
    @Autowired
    private XPathOperations xpathTemplate;

    @Value("${appdirect.events.account.notice}")
    private String noticeXPath;

    public Subscription buildSubscription(String event) {
        return getSubscriptionWithGivenId(event,UUID.randomUUID().toString());
    }

    public Subscription retrieveSubscription(String event){
        String id=getXpath(event,accountIDXPath);
        LoggerUtils.logInfo(logger,"subscription %s received",id);
        return getSubscriptionWithGivenId(event,id);
    }

    public String getNotice(String event){
        String notice=getXpath(event,noticeXPath);
    }


    private Subscription getSubscriptionWithGivenId(String event,String id) {
        Subscription subscription=new Subscription();

        String pricing = getXpath(event,pricingXPath);
        String subscriptionType=getXpath(event,editionCodeXPath);

        subscription.setId(id);
        subscription.setPricing(pricing);
        subscription.setSubscriptionType(subscriptionType);
        subscription.setSubscriptionStatus(ACTIVE);
        LoggerUtils.logDebug(logger, "subscription %s created", subscription.toString());
        return subscription;
    }

    private String getXpath(String event,String xpath) {
        Source src = new StreamSource(new java.io.StringReader(event));
        return xpathTemplate.evaluateAsString(xpath,src);
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
