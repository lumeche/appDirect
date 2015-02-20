package com.appdirect.model.subscription;

/**
 * Created by Luis Tobon on 2015-02-19.
 */
public interface ISubscriptionFactory {
    Subscription buildNewSubscription(String event);

    Subscription retrieveExistingSubscription(String event);

    String getSubscriptionId(String event);

    String getNotice(String event);
}
