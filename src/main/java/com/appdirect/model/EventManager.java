package com.appdirect.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.xml.xpath.XPathOperations;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * Created by Luis Tobon on 2015-02-16.
 */

@Component
public class EventManager {



    @Autowired
    private XPathOperations xpathTemplate;


    public String getXpath(String event, String xpath) {
        Source src = new StreamSource(new java.io.StringReader(event));
        return xpathTemplate.evaluateAsString(xpath, src);
    }

    public void setXpathTemplate(XPathOperations xpathTemplate) {
        this.xpathTemplate = xpathTemplate;
    }
}
