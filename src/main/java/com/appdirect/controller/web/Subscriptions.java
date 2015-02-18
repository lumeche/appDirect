package com.appdirect.controller.web;

import com.appdirect.model.subscription.Subscription;
import com.appdirect.model.subscription.SubscriptionManager;
import com.appdirect.model.user.User;
import com.appdirect.model.user.UserManagement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@Controller
public class Subscriptions {

    final static Logger logger = LoggerFactory.getLogger(IsAlive.class);

    @Autowired
    private SubscriptionManager subscriptionManager;

    @Autowired
    private UserManagement userManagement;

    @RequestMapping("/subscriptions")
    public String greeting(Model model){
        model.addAttribute("subscriptions",subscriptionManager.getSubscriptions());
        model.addAttribute("users",userManagement.getSubscriptionForUser().keySet());
       // model.addAttribute("usersSubscribed",buildSubscriptioinsForUserId(userManagement.getSubscriptionForUser()));
        model.addAttribute("usersSubscribed",userManagement.getSubscriptionForUser());
        return "subscriptions";
    }

//    private Map<String, List<Subscription>> buildSubscriptioinsForUserId(Map<User, List<Subscription>> subscriptionForUser) {
//        Map<String,List<Subscription>> map=new HashMap<String,List<Subscription>>();
//        for (Map.Entry<User, List<Subscription>> entry:subscriptionForUser.entrySet()){
//            map.put(entry.getKey().getUserId(),entry.getValue());
//        }
//        return map;
//    }

    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }
}
