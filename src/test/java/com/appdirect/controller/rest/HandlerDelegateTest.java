package com.appdirect.controller.rest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HandlerDelegateTest {

    private static final String RESPONSE = "html response";
    private HandlerDelegate testee;
    private RestTemplate restTemplate;

    @Before
    public void setUp(){
        restTemplate=mock(RestTemplate.class);
        testee=new HandlerDelegate();
        testee.setOauthRestTemplate(restTemplate);
    }


    @Test
    public void testGetEventInfo(){
        //when
        when(restTemplate.exchange(anyString(), any(HttpMethod.class), any(HttpEntity.class), eq(String.class), anyString())).thenReturn(new ResponseEntity<String>(RESPONSE, HttpStatus.ACCEPTED));
        //do
        String result=testee.getEventInfo("XXXX");
        //then
        assertThat(result, is(RESPONSE));
    }

    @Test
    public void testIsDummyRequest(){
        assertThat(testee.isDummyRequest("adsfdummy"), is(true));
        assertThat(testee.isDummyRequest("dummyadsf"), is(true));
        assertThat(testee.isDummyRequest("Dummyadsf"), is(true));
        assertThat(testee.isDummyRequest("adsf"), is(false));
    }

    @Test
    public void testBuildForbidenResponse(){
        //do
        ResponseEntity response = testee.buildForbiddenHTTPResponse();
        //then
        assertThat(response.getStatusCode(),is(HttpStatus.FORBIDDEN));
    }

}