package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.SubcriptionCreated;
import com.appdirect.controller.rest.payloads.SubcriptionResponse;
import com.appdirect.model.subscription.SubscriptionManager;
import com.appdirect.model.utils.LoggerUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Source;

/**
 * Created by Luis Tobon on 2015-02-14.
 */
@RestController
public class SubscriptionHandler extends AbstractHandler{
    final static Logger logger = LoggerFactory.getLogger(SubscriptionHandler.class);

    @Autowired
    private SubscriptionManager subscriptionManager;

    @RequestMapping("/subscription/create")
    public SubcriptionCreated subscriptionCreate(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription create received. Token: %s",token);
        Source event = getEventInfo(token);
        String id=subscriptionManager.createSubscription(event);
        if(StringUtils.isNotEmpty(id)){
            return new SubcriptionCreated(Boolean.TRUE.toString(),"",id);
        }else{
            return new SubcriptionCreated(Boolean.FALSE.toString(),"",id);
        }
    }


    @RequestMapping("/subscription/change")
    public SubcriptionResponse subscriptionChange(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription change received. Token: %s",token);
        Source event = getEventInfo(token);
        subscriptionManager.updateSubscription(event);
        return buildResponse();
    }

    @RequestMapping("/subscription/cancel")
    public SubcriptionResponse subscriptionCancel(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription delete received. Token: %s",token);
        Source event = getEventInfo(token);
        subscriptionManager.deleteSubscription(event);
        return buildResponse();
    }

    @RequestMapping("/subscription/status")
    public SubcriptionResponse subscriptionStatus(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription status     received. Token: %s",token);
        Source event = getEventInfo(token);
        subscriptionManager.updateStatusSubscriptions(event);
        return buildResponse();
    }


    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }
}
