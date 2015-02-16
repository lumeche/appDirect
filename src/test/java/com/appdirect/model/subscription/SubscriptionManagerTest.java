package com.appdirect.model.subscription;
import static org.hamcrest.CoreMatchers.*;
import com.appdirect.Application;
import org.apache.commons.io.FileUtils;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.xml.transform.stream.StreamSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
public class SubscriptionManagerTest {
    @Autowired
    private SubscriptionManager subscriptionManager;

    @Test
    public void testCreateSubscription() throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();

        String xml=FileUtils.readFileToString(new File(classLoader.getResource("events/event-order.xml").getFile()));

        String id=subscriptionManager.createSubscription(xml);
        assertThat(id, is(notNullValue()));
    }

    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }
}