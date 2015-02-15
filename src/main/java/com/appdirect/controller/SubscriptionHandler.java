package com.appdirect.controller;

import com.appdirect.controller.response.AccountResponse;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by NENE on 2015-02-14.
 */

@RestController
public class SubscriptionHandler {
    final static Logger logger = LoggerFactory.getLogger(IsAlive.class);

    @RequestMapping("/subscription/create")
    public AccountResponse subscriptionCreate(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription create received");
        return buildAccountResponse();
    }


    @RequestMapping("/subscription/change")
    public AccountResponse subscriptionChange(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription change received");
        return buildAccountResponse();
    }

    @RequestMapping("/subscription/cancel")
    public AccountResponse subscriptionCancel(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription delete received");
        return buildAccountResponse();
    }

    @RequestMapping("/subscription/status")
    public AccountResponse subscriptionStatus(@RequestParam(value = "token",required = true)String token,Model model){
        LoggerUtils.logDebug(logger,"Subscription status     received");
        return buildAccountResponse();
    }

    //FIXME addTest units for this
    //FIXME delete this method
    private AccountResponse buildAccountResponse() {
        AccountResponse resp=new AccountResponse();
        resp.setAccountIdentifier(UUID.randomUUID().toString());
        resp.setMessage("----------------");
        resp.setSuccess(true);
        return resp;
    }
}
