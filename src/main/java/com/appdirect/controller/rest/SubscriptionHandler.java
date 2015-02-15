package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.SubcriptionResponse;
import com.appdirect.controller.rest.payloads.event.generated.EventType;
import com.appdirect.model.ErrorCode;
import com.appdirect.model.subscription.SubscriptionManager;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Luis Tobon on 2015-02-14.
 */
@RestController
public class SubscriptionHandler extends AbstractHandler{
    final static Logger logger = LoggerFactory.getLogger(SubscriptionHandler.class);

    @Autowired
    private SubscriptionManager subscriptionManager;

    @RequestMapping("/subscription/create")
    public SubcriptionResponse subscriptionCreate(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription create received. Token: %s",token);
        EventType event = getEventInfo(token);
        subscriptionManager.createSubscription(event);
        return buildAccountResponse();
    }


    @RequestMapping("/subscription/change")
    public SubcriptionResponse subscriptionChange(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription change received. Token: %s",token);
        EventType event = getEventInfo(token);
        subscriptionManager.updateSubscription(event);
        return buildAccountResponse();
    }

    @RequestMapping("/subscription/cancel")
    public SubcriptionResponse subscriptionCancel(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription delete received. Token: %s",token);
        EventType event = getEventInfo(token);
        subscriptionManager.deleteSubscription(event);
        return buildAccountResponse();
    }

    @RequestMapping("/subscription/status")
    public SubcriptionResponse subscriptionStatus(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription status     received. Token: %s",token);
        EventType event = getEventInfo(token);
        subscriptionManager.updateStatusSubscriptions(event);
        return buildAccountResponse();
    }


    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }
}
