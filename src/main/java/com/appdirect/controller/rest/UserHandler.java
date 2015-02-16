package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.SubcriptionResponse;
import com.appdirect.model.user.UserManagement;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.transform.Source;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@RestController
public class UserHandler extends AbstractHandler{

    final static Logger logger = LoggerFactory.getLogger(UserHandler.class);

    @Autowired
    private UserManagement userManagement;

    @RequestMapping("/user/assign")
    public SubcriptionResponse userAssign(@RequestParam(value = "token",required = true)String token){
        LoggerUtils.logDebug(logger, "User assign received. Token: %s", token);
        Source event=getEventInfo(token);
        userManagement.assignUser(event);
        return buildResponse();
    }

    @RequestMapping("/user/unassign")
    public SubcriptionResponse userUnAssign(@RequestParam(value = "token",required = true)String token){
        LoggerUtils.logDebug(logger, "User unassigned received. Token: %s", token);
        Source event=getEventInfo(token);
        userManagement.unassignUser(event);
        return buildResponse();
    }

    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }
}
