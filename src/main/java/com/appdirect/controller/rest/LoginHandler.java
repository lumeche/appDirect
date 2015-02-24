package com.appdirect.controller.rest;

import com.appdirect.model.user.UserManager;
import com.appdirect.model.utils.LoggerUtils;
import org.openid4java.message.ParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Luis Tobon on 2015-02-23.
 */

@RestController
public class LoginHandler {
    final static Logger logger = LoggerFactory.getLogger(LoginHandler.class);
    private static final String VERIFY_RESPONSE_URL = "/verify";

    @Autowired
    private HandlerDelegate handlerDelegate;
    @Autowired
    private UserManager userManager;

    @RequestMapping("/login")
    public ResponseEntity<String> subscriptionCreate(@RequestHeader("Authorization") String authorization,
                                                     @RequestParam(value = "openid", required = true) String openId,
                                                     @RequestParam(value = "accountId", required = true) String accountId,
                                                     HttpServletRequest request, HttpServletResponse response, Model model) {

        LoggerUtils.logDebug(logger, "login request for user %s, with openId %s", accountId, openId);
        boolean authentication = userManager.authenticateUser(accountId, openId, request.getRequestURI() + VERIFY_RESPONSE_URL,
                request, response);
        return handlerDelegate.buildHTTPResponse(String.valueOf(authentication));
    }

    @RequestMapping("/login" + VERIFY_RESPONSE_URL)
    ResponseEntity<String> verifySubscription(HttpServletRequest request) {
        String userIdentifier=userManager.verifyAuthentication(request);
        return handlerDelegate.buildHTTPResponse(String.valueOf(userIdentifier));
    }

}
