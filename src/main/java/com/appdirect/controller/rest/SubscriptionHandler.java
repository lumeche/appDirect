package com.appdirect.controller.rest;

import com.appdirect.controller.rest.response.SubcriptionResponse;
import com.appdirect.controller.web.IsAlive;
import com.appdirect.model.ErrorCode;
import com.appdirect.model.SubscriptionManager;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by NENE on 2015-02-14.
 */

@RestController
public class SubscriptionHandler {
    final static Logger logger = LoggerFactory.getLogger(SubscriptionHandler.class);

    @Autowired
    private SubscriptionManager subscriptionManager;

    @RequestMapping("/subscription/create")
    public SubcriptionResponse subscriptionCreate(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription create received. Token: %s",token);
        return buildAccountResponse();
    }


    @RequestMapping("/subscription/change")
    public SubcriptionResponse subscriptionChange(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription change received. Token: %s",token);
        return buildAccountResponse();
    }

    @RequestMapping("/subscription/cancel")
    public SubcriptionResponse subscriptionCancel(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription delete received. Token: %s",token);
        return buildAccountResponse();
    }

    @RequestMapping("/subscription/status")
    public SubcriptionResponse subscriptionStatus(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription status     received. Token: %s",token);
        return buildAccountResponse();
    }

    //FIXME addTest units for this
    //FIXME delete this method
    private SubcriptionResponse buildAccountResponse() {
        SubcriptionResponse resp=new SubcriptionResponse();
        resp.setErrorCode(ErrorCode.UNKNOWN_ERROR);
        resp.setMessage("----------------");
        resp.setSuccess(true);
        return resp;
    }

    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }
}
