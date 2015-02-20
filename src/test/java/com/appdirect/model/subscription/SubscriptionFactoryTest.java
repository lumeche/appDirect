package com.appdirect.model.subscription;

import com.appdirect.Application;
import com.appdirect.model.EventManager;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SubscriptionFactoryTest {
    private static final java.lang.String DEFAULT_STRING = "XXXXX";
    private SubscriptionFactory testee;
    private EventManager eventManager;

    @Before
    public void setUp(){
        testee=new SubscriptionFactory();
        eventManager= mock(EventManager.class);
        testee.setEventManager(eventManager);

        //when
        when(eventManager.getXpath(anyString(),anyString())).thenReturn(DEFAULT_STRING);
    }
    @Test
    public void testBuildNewSubscription() throws Exception {
        //do
        Subscription subs=testee.buildNewSubscription("xxxx");
        //then
        assertThat(subs.getId().length(), is(UUID.randomUUID().toString().length()));
        assertThat(subs.getPricing(),is(DEFAULT_STRING));
        assertThat(subs.getSubscriptionStatus(),is(DEFAULT_STRING));
        assertThat(subs.getSubscriptionType(),is(DEFAULT_STRING));
    }

    @Test
    public void testRetrieveExistingSubscription() throws Exception {
        //do
        Subscription sub=testee.retrieveExistingSubscription("xxxx");
        //then
        assertThat(sub.getId(),is(DEFAULT_STRING));
        assertThat(sub.getSubscriptionStatus(),is(DEFAULT_STRING));
        assertThat(sub.getSubscriptionType(),is(DEFAULT_STRING));
        assertThat(sub.getPricing(),is(DEFAULT_STRING));
    }

}