package com.appdirect.model.user;

import com.appdirect.model.subscription.Subscription;
import com.appdirect.model.subscription.SubscriptionManager;
import com.appdirect.model.utils.LoggerUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@Component
public class UserManagement {

    final static Logger logger = LoggerFactory.getLogger(UserManagement.class);

    private Map<User,List<Subscription>> usersMap=Collections.synchronizedMap(new HashMap<User,List<Subscription>>());

    @Autowired
    private SubscriptionManager subscriptionManager;

    @Autowired
    private IUserFactory userFactory;

    public boolean assignUser(String event){
        User user=findUser(event);
        Subscription subscription=subscriptionManager.findSubscription(event);
        if(user==null || subscription==null){
            LoggerUtils.logError(logger,"Either user or subscription is null");
            return false;
        }
        List<Subscription> subscriptionsForUser = usersMap.get(user);
        subscriptionsForUser.add(subscription);
        LoggerUtils.logDebug(logger, "User %s assigned to subscription %s",user.toString(),subscription.toString());
        return true;
    }

    public boolean unassignUser(String event){
        User user=findUser(event);
        Subscription subscription=subscriptionManager.findSubscription(event);
        List<Subscription> subscriptions = usersMap.get(user);
        if(subscription==null || user==null){
            LoggerUtils.logDebug(logger,"Either user or subscription is null");
            return false;
        }
        if(subscriptions.remove(subscription)){
            LoggerUtils.logDebug(logger,"User %s unassigned from subscription %s",user.toString(),subscription.toString());
            return true;
        }else{
            LoggerUtils.logDebug(logger,"Subscription %s don't found for User %s",subscription.toString(),user.toString());
            return false;
        }
    }

    private User findUser(String event) {
        final String userId=userFactory.getUserId(event);
        if(!usersMap.containsKey(new User(userId))){
            usersMap.put(userFactory.buildUser(event),new ArrayList<Subscription>());
        }
        return CollectionUtils.find(usersMap.keySet(), new Predicate<User>() {
            @Override
            public boolean evaluate(User candidateUser) {
                return candidateUser.getUserId().equals(userId);
            }
        });
    }

    public Map<User,List<Subscription>> getSubscriptionForUser(){
        HashMap<User, List<Subscription>> newMap = new HashMap<User, List<Subscription>>();
        for (Map.Entry<User,List<Subscription>> entry:usersMap.entrySet()){
            newMap.put(entry.getKey(),new ArrayList<Subscription>(entry.getValue()));
        }
        return newMap;
    }

    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }


    public void setUserFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }
}
