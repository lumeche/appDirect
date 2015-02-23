package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.UserResult;
import com.appdirect.model.user.UserManagement;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;


public class UserHandlerTest {
    private static final java.lang.String DEFAULT_RESPONSE = "XXXXXX";

    private UserHandler testee;
    private HandlerDelegate handlerDelegate;
    private UserManagement userManagement;

    @Before
    public void setUp() throws Exception {
        testee = new UserHandler();
        userManagement = mock(UserManagement.class);
        handlerDelegate = mock(HandlerDelegate.class);
        testee.setHandlerDelegate(handlerDelegate);
        testee.setUserManagement(userManagement);

        when(handlerDelegate.getEventInfo(anyString())).thenReturn(DEFAULT_RESPONSE);
        when(handlerDelegate.isDummyRequest(anyString())).thenReturn(false);
        when(handlerDelegate.buildHTTPResponse(anyObject())).thenCallRealMethod();
        when(handlerDelegate.isInvalidSignature(anyString(),anyString())).thenReturn(false);
        when(handlerDelegate.sendGetRequest(anyString())).thenReturn(new ResponseEntity<String>(HttpStatus.ACCEPTED));
    }

    @Test
    public void testUserAssign() throws Exception {
        //when
        ResponseEntity<UserResult> response = testee.userAssign("xx", "xxx",null);
        //do
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().isSuccess(),is(true));
    }

    @Test
    public void testUserUnAssign() throws Exception {
        //do
        ResponseEntity<UserResult> response = testee.userUnAssign("xxx","wewd",null);
        //then
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody().isSuccess(),is(true));
    }

    @Test
    public void testDummy() throws Exception {
        //when
        when(handlerDelegate.isDummyRequest(anyString())).thenReturn(true);
        InOrder inOrder=inOrder(userManagement);
        //do
        ResponseEntity<UserResult> response1 = testee.userAssign("asdf", "SDF",null);
        ResponseEntity<UserResult> response2 = testee.userUnAssign("asdf","SFD",null);
        //thenassertThat(response1.getStatusCode(), is(HttpStatus.ACCEPTED));
        assertThat(response1.getBody().isSuccess(),is(true));
        assertThat(response2.getStatusCode(), is(HttpStatus.OK));
        assertThat(response2.getBody().isSuccess(),is(true));
        inOrder.verify(userManagement,never()).unassignUser(anyString());
        inOrder.verify(userManagement,never()).assignUser(anyString());
    }
}