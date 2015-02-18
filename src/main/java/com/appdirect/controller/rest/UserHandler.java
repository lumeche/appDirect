package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.SubcriptionResponse;
import com.appdirect.controller.rest.payloads.UserResult;
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
    public UserResult userAssign(@RequestParam(value = "token",required = true)String token){
        LoggerUtils.logDebug(logger, "User assign received. Token: %s", token);
        String event=getEventInfo(token);
        boolean status=userManagement.assignUser(event);
        return buildUserResult(status);
    }


    @RequestMapping("/user/unassign")
    public UserResult userUnAssign(@RequestParam(value = "token",required = true)String token){
        LoggerUtils.logDebug(logger, "User unassigned received. Token: %s", token);
        String event=getEventInfo(token);
        boolean status=userManagement.unassignUser(event);
        return buildUserResult(status);
    }


    private UserResult buildUserResult(boolean status) {
        if (status){
            return new UserResult(true);
        }else{
            LoggerUtils.logError(logger,"Returning true even if operation failed since we don't have persistence");
            return new UserResult(true);
        }
    }

    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }
}
