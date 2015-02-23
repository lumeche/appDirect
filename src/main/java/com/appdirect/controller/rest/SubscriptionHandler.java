package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.SubscriptionCreated;
import com.appdirect.controller.rest.payloads.SubscriptionResponse;
import com.appdirect.model.ErrorCode;
import com.appdirect.model.subscription.SubscriptionManager;
import com.appdirect.model.utils.LoggerUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Luis Tobon on 2015-02-14.
 */
@RestController
public class SubscriptionHandler {
    final static Logger logger = LoggerFactory.getLogger(SubscriptionHandler.class);

    @Autowired
    private HandlerDelegate handlerDelegate;

    @Autowired
    private SubscriptionManager subscriptionManager;

    @RequestMapping("/subscription/create")
    public ResponseEntity<SubscriptionCreated> subscriptionCreate(@RequestHeader("Authorization") String authorization,
                                                                  @RequestParam(value = "token", required = true) String token,
                                                                  HttpServletRequest request, Model model) {
        LoggerUtils.logDebug(logger, "Subscription create received. Token: %s", token);
        if(handlerDelegate.isInvalidSignature(authorization,handlerDelegate.getFullRequestURL(request))) return buildForbiddenHTTPResponse();
        if (isDummyRequest(token)) return buildHTTPResponse(new SubscriptionCreated());
        String event = getEventInfo(token);
        String id = subscriptionManager.createSubscription(event);
        if (StringUtils.isNotEmpty(id)) {
            return buildHTTPResponse(new SubscriptionCreated(id));
        } else {
            LoggerUtils.logError(logger, "Returning true even if operation failed since we don't have persistence");
            return buildHTTPResponse(new SubscriptionCreated(id));
        }
    }



    @RequestMapping("/subscription/change")
    public ResponseEntity<SubscriptionResponse> subscriptionChange(@RequestHeader("Authorization") String authorization,
                                                                   @RequestParam(value = "token", required = true) String token,
                                                                   HttpServletRequest request,
                                                                   Model model) {
        LoggerUtils.logDebug(logger, "Subscription change received. Token: %s", token);
        if(handlerDelegate.isInvalidSignature(authorization,handlerDelegate.getFullRequestURL(request))) return buildForbiddenHTTPResponse();
        if (isDummyRequest(token)) return buildHTTPResponse(new SubscriptionResponse(true));
        String event = getEventInfo(token);
        boolean status = subscriptionManager.updateSubscription(event);
        return buildSubscriptionResponse(status);
    }


    @RequestMapping("/subscription/cancel")
    public ResponseEntity<SubscriptionResponse> subscriptionCancel(@RequestHeader("Authorization") String authorization,
                                                                   @RequestParam(value = "token", required = true) String token,
                                                                   HttpServletRequest request,
                                                                   Model model) {
        LoggerUtils.logDebug(logger, "Subscription delete received. Token: %s", token);
        if(handlerDelegate.isInvalidSignature(authorization, request.getContextPath())) return buildForbiddenHTTPResponse();
        if (isDummyRequest(token)) return buildHTTPResponse(new SubscriptionResponse(true));
        String event = getEventInfo(token);
        boolean status = subscriptionManager.deleteSubscription(event);
        return buildSubscriptionResponse(status);
    }

    @RequestMapping("/subscription/status")
    public ResponseEntity<SubscriptionResponse> subscriptionStatus(@RequestHeader("Authorization") String authorization,
                                                                   @RequestParam(value = "token", required = true) String token,
                                                                   HttpServletRequest request,
                                                                   Model model) {
        LoggerUtils.logDebug(logger, "Subscription status     received. Token: %s", token);
        if(handlerDelegate.isInvalidSignature(authorization,request.getRequestURI())) return buildForbiddenHTTPResponse();
        if (isDummyRequest(token)) return buildHTTPResponse(new SubscriptionResponse(true));
        String event = getEventInfo(token);
        boolean status = subscriptionManager.updateStatusSubscriptions(event);
        return buildSubscriptionResponse(status);
    }


    public ResponseEntity<SubscriptionResponse> buildSubscriptionResponse(boolean status) {
        if (status) {
            return buildHTTPResponse(new SubscriptionResponse(true, ErrorCode.UNKNOWN_ERROR, ""));
        } else {
            LoggerUtils.logError(logger, "Returning true even if operation failed since we don't have persistence");
            return buildHTTPResponse(new SubscriptionResponse(true, ErrorCode.UNKNOWN_ERROR, ""));
        }
    }

    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    protected String getEventInfo(String token) {
        return handlerDelegate.getEventInfo(token);
    }

    protected boolean isDummyRequest(String token) {
        return handlerDelegate.isDummyRequest(token);
    }

    protected ResponseEntity buildForbiddenHTTPResponse() {
        return handlerDelegate.buildForbiddenHTTPResponse();
    }

    protected ResponseEntity buildHTTPResponse(Object response) {
        return handlerDelegate.buildHTTPResponse(response);
    }

    public void setHandlerDelegate(HandlerDelegate handlerDelegate) {
        this.handlerDelegate = handlerDelegate;
    }

}
