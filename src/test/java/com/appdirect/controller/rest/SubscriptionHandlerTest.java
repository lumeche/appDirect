package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.SubscriptionCreated;
import com.appdirect.controller.rest.payloads.SubscriptionResponse;
import com.appdirect.model.subscription.Subscription;
import com.appdirect.model.subscription.SubscriptionManager;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class SubscriptionHandlerTest {
    private static final java.lang.String DEFAULT_RESPONSE = "XXXXXX";
    private SubscriptionHandler testee;
    private SubscriptionManager subscriptionManager;
    private HandlerDelegate handlerDelegate;

    @Before
    public void setUp() throws Exception {
        testee = new SubscriptionHandler();
        subscriptionManager=mock(SubscriptionManager.class);
        handlerDelegate=mock(HandlerDelegate.class);
        testee.setSubscriptionManager(subscriptionManager);
        testee.setHandlerDelegate(handlerDelegate);

        when(handlerDelegate.getEventInfo(anyString())).thenReturn(DEFAULT_RESPONSE);
        when(handlerDelegate.isDummyRequest(anyString())).thenReturn(false);
        when(handlerDelegate.buildHTTPResponse(anyObject())).thenCallRealMethod();
        when(handlerDelegate.isInvalidSignature(anyString())).thenReturn(false);
        when(handlerDelegate.sendGetRequest(anyString())).thenReturn(new ResponseEntity<String>(HttpStatus.ACCEPTED));
    }

    @Test
    public void testSubscriptionCreate() throws Exception {
        //when
        InOrder inOrder=inOrder(subscriptionManager);
        //do
        ResponseEntity<SubscriptionCreated> response = testee.subscriptionCreate("xxx", "xxx", mock(Model.class));
        //then
        assertThat(response.getBody().getSuccess(), is(true));
        inOrder.verify(subscriptionManager).createSubscription(anyString());
    }

    @Test
    public void testSubscriptionChange() throws Exception {
        //when
        InOrder inOrder=inOrder(subscriptionManager);
        //do
        ResponseEntity<SubscriptionResponse> response = testee.subscriptionChange("xxx", "xxx", mock(Model.class));
        //then
        assertThat(response.getBody().isSuccess(), is(true));
        inOrder.verify(subscriptionManager).updateSubscription(anyString());
    }

    @Test
    public void testSubscriptionCancel() throws Exception {
        //when
        InOrder inOrder=inOrder(subscriptionManager);
        //do
        ResponseEntity<SubscriptionResponse> response = testee.subscriptionCancel("xxx", "xxx", mock(Model.class));
        //then
        assertThat(response.getBody().isSuccess(), is(true));
        inOrder.verify(subscriptionManager).deleteSubscription(anyString());
    }

    @Test
    public void testSubscriptionStatus() throws Exception {
        //when
        InOrder inOrder=inOrder(subscriptionManager);
        //do
        ResponseEntity<SubscriptionResponse> response = testee.subscriptionStatus("xxx", "xxx", mock(Model.class));
        //then
        assertThat(response.getBody().isSuccess(), is(true));
        inOrder.verify(subscriptionManager).updateStatusSubscriptions(anyString());
    }


}