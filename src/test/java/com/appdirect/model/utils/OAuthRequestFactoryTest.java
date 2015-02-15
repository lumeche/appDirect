package com.appdirect.model.utils;

import com.appdirect.Application;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class OAuthRequestFactoryTest {

    @Autowired
    private OAuthRequestFactory oauthRequestFactory;

    @Value("${oauth.consumer.key}")
    private String key;
    @Value("${oauth.consumer.secret}")
    private String secret;


    @Test
    public void testInjection() {
        Assert.assertThat(oauthRequestFactory.getKey(), CoreMatchers.is(key));
        Assert.assertThat(oauthRequestFactory.getSecret(), CoreMatchers.is(secret));
    }

    public void setOauthRequestFactory(OAuthRequestFactory oauthRequestFactory) {
        this.oauthRequestFactory = oauthRequestFactory;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
}
