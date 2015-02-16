package com.appdirect.controller.web;

import com.appdirect.model.subscription.SubscriptionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@Controller
public class Subscriptions {

    final static Logger logger = LoggerFactory.getLogger(IsAlive.class);

    @Autowired
    SubscriptionManager subscriptionManager;

    @RequestMapping("/subscriptions")
    public String greeting(Model model){
        model.addAttribute("subscriptions",subscriptionManager.getSubscriptions());
        return "subscriptions";
    }

    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }
}
