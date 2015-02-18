package com.appdirect.controller.rest;

import com.appdirect.controller.rest.payloads.SubscriptionResponse;
import com.appdirect.controller.rest.payloads.UserResult;
import com.appdirect.model.user.UserManagement;
import com.appdirect.model.utils.LoggerUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@RestController
public class UserHandler extends AbstractHandler{

    final static Logger logger = LoggerFactory.getLogger(UserHandler.class);

    @Autowired
    private UserManagement userManagement;

    @RequestMapping("/user/assign")
    public ResponseEntity<UserResult>  userAssign(@RequestHeader("Authorization") String authorization, @RequestParam(value = "token",required = true)String token){
        LoggerUtils.logDebug(logger, "User assign received. Token: %s", token);
        if(isInvalidSignature(authorization)) return buildForbiddenHTTPResponse();
        if (isDummyRequest(token)) return buildHTTPResponse(new UserResult(true));
        String event=getEventInfo(token);
        boolean status=userManagement.assignUser(event);
        return buildUserResult(status);
    }



    @RequestMapping("/user/unassign")
    public ResponseEntity<UserResult>  userUnAssign(@RequestHeader("Authorization") String authorization, @RequestParam(value = "token",required = true)String token){
        LoggerUtils.logDebug(logger, "User unassigned received. Token: %s", token);
        if(isInvalidSignature(authorization)) return buildForbiddenHTTPResponse();
        if (isDummyRequest(token)) return buildHTTPResponse(new UserResult(true));
        String event=getEventInfo(token);
        boolean status=userManagement.unassignUser(event);
        return buildUserResult(status);
    }


    private ResponseEntity buildUserResult(boolean status) {
        if (status){
            return buildHTTPResponse(new UserResult(true));
        }else{
            LoggerUtils.logError(logger,"Returning true even if operation failed since we don't have persistence");
            return buildHTTPResponse(new UserResult(true));
        }
    }

    public void setUserManagement(UserManagement userManagement) {
        this.userManagement = userManagement;
    }
}
