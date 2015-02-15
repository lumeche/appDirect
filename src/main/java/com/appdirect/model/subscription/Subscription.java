package com.appdirect.model.subscription;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
public class Subscription {
    private String id;
    private String subscriptionType;
    private String subscriptionStatus;
    private int usersInSubcription;
    private int usersAssignedInSubcription;
    private String pricing;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionStatus() {
        return subscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        this.subscriptionStatus = subscriptionStatus;
    }

    public int getUsersInSubcription() {
        return usersInSubcription;
    }

    public void setUsersInSubcription(int usersInSubcription) {
        this.usersInSubcription = usersInSubcription;
    }

    public int getUsersAssignedInSubcription() {
        return usersAssignedInSubcription;
    }

    public void setUsersAssignedInSubcription(int usersAssignedInSubcription) {
        this.usersAssignedInSubcription = usersAssignedInSubcription;
    }

    public String getPricing() {
        return pricing;
    }

    public void setPricing(String pricing) {
        this.pricing = pricing;
    }
}
