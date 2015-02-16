package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.SubcriptionResponse;
import com.appdirect.controller.rest.payloads.event.generated.EventType;
import com.appdirect.model.ErrorCode;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Source;

/**
 * Created by Luis Tobon on 2015-02-15.
 */

public abstract class AbstractHandler {

    final static Logger logger = LoggerFactory.getLogger(AbstractHandler.class);

    @Autowired
    private RestTemplate oauthRestTemplate;

    @Value(value = "${appdirect.events.url}")
    private String appDirectEventsURL;

    protected Source getEventInfo(String token) {
        LoggerUtils.logDebug(logger, "About to send GET request to %s with token %s", appDirectEventsURL, token);
        Source event=oauthRestTemplate.getForObject(appDirectEventsURL,Source.class,token);
        LoggerUtils.logDebug(logger,"Event  returned %s",event.toString());
        return event;
    }


    //FIXME addTest units for this
    //FIXME delete this method
    protected SubcriptionResponse buildResponse() {
        SubcriptionResponse resp=new SubcriptionResponse();
        resp.setErrorCode(ErrorCode.UNKNOWN_ERROR);
        resp.setMessage("----------------");
        resp.setSuccess(true);
        return resp;
    }

}
