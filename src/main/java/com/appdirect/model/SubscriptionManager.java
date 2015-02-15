package com.appdirect.model;

import com.appdirect.controller.web.IsAlive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by NENE on 2015-02-14.
 */
@Component
public class SubscriptionManager {

    final static Logger logger = LoggerFactory.getLogger(SubscriptionManager.class);

    public boolean createSubscription(String token){
        return false;
    }

    public boolean updateSubscription(String token){
        return false;
    }

    public boolean deleteSubscription(String token){
        return false;
    }

}
