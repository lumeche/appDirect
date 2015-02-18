package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.SubcriptionCreated;
import com.appdirect.controller.rest.payloads.SubcriptionResponse;
import com.appdirect.model.ErrorCode;
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
public class SubscriptionHandler extends AbstractHandler {
    final static Logger logger = LoggerFactory.getLogger(SubscriptionHandler.class);
    private static final String DEFAULT_ERROR_MESSAGE = "Returning true since we don't have persistence";

    @Autowired
    private SubscriptionManager subscriptionManager;

    @RequestMapping("/subscription/create")
    public SubcriptionCreated subscriptionCreate(@RequestParam(value = "token", required = true) String token, Model model) {
        LoggerUtils.logDebug(logger, "Subscription create received. Token: %s", token);
        String event = getEventInfo(token);
        String id = subscriptionManager.createSubscription(event);
        if (StringUtils.isNotEmpty(id)) {
            return new SubcriptionCreated(Boolean.TRUE, "", id);
        } else {
            LoggerUtils.logError(logger,"Returning true even if operation failed since we don't have persistence");
            return new SubcriptionCreated(Boolean.TRUE, DEFAULT_ERROR_MESSAGE, id);
        }
    }


    @RequestMapping("/subscription/change")
    public SubcriptionResponse subscriptionChange(@RequestParam(value = "token", required = true) String token, Model model) {
        LoggerUtils.logDebug(logger, "Subscription change received. Token: %s", token);
        String event = getEventInfo(token);
        boolean status = subscriptionManager.updateSubscription(event);
        return buildSubscriptionResponse(status);

    }


    @RequestMapping("/subscription/cancel")
    public SubcriptionResponse subscriptionCancel(@RequestParam(value = "token", required = true) String token, Model model) {
        LoggerUtils.logDebug(logger, "Subscription delete received. Token: %s", token);
        String event = getEventInfo(token);
        boolean status = subscriptionManager.deleteSubscription(event);
        return buildSubscriptionResponse(status);
    }

    @RequestMapping("/subscription/status")
    public SubcriptionResponse subscriptionStatus(@RequestParam(value = "token", required = true) String token, Model model) {
        LoggerUtils.logDebug(logger, "Subscription status     received. Token: %s", token);
        String event = getEventInfo(token);
        boolean status = subscriptionManager.updateStatusSubscriptions(event);
        return buildSubscriptionResponse(status);
    }


    private SubcriptionResponse buildSubscriptionResponse(boolean status) {
        if(status) {
            return new SubcriptionResponse(true, ErrorCode.UNKNOWN_ERROR, "");
        }else{
            LoggerUtils.logError(logger,"Returning true even if operation failed since we don't have persistence");
            return new SubcriptionResponse(true, ErrorCode.UNKNOWN_ERROR, DEFAULT_ERROR_MESSAGE);
        }
    }

    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }
}
