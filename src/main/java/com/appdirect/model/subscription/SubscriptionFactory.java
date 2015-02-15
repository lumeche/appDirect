package com.appdirect.model.subscription;

import com.appdirect.controller.rest.payloads.event.generated.EventType;
import org.springframework.stereotype.Component;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@Component
public class SubscriptionFactory {
    public Subscription buildSubscription(EventType event) {
        return null;
    }
}
