package com.appdirect.controller.web;

import com.appdirect.model.subscription.Subscription;
import com.appdirect.model.subscription.SubscriptionManager;
import com.appdirect.model.user.User;
import com.appdirect.model.user.UserManagement;
import com.appdirect.model.utils.LoggerUtils;
import org.apache.commons.collections4.Closure;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
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
        Map<User, List<Subscription>> userSubscriptions = userManagement.getSubscriptionForUser();
        model.addAttribute("subscriptions",subscriptionManager.getSubscriptions());
        model.addAttribute("users", userSubscriptions.keySet());
       // model.addAttribute("usersSubscribed",buildSubscriptioinsForUserId(userManagement.getSubscriptionForUser()));
        model.addAttribute("usersSubscribed",userSubscriptions);
        printUserSubscriptions(userSubscriptions);
        return "subscriptions";
    }

    private void printUserSubscriptions(Map<User, List<Subscription>> userSubscriptions) {
        CollectionUtils.forAllDo(userSubscriptions.entrySet(),new Closure<Map.Entry<User, List<Subscription>>>() {
            @Override
            public void execute(Map.Entry<User, List<Subscription>> input) {
                LoggerUtils.logDebug(logger,"For user %s, %d subscriptions assigned",input.getKey().getUserId(),input.getValue().size());
            }
        });
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
