package com.appdirect.model.user;

import com.appdirect.controller.rest.payloads.event.generated.EventType;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@Component
public class UserManagement {

    final static Logger logger = LoggerFactory.getLogger(UserManagement.class);

    public boolean assignUser(EventType event){
        LoggerUtils.logDebug(logger,"User %s assigned",event.toString());
        return false;
    }

    public boolean unassignUser(EventType event){
        LoggerUtils.logDebug(logger,"User %s unassigned",event.toString());
        return false;
    }
}