package com.appdirect.controller.rest.response;

import com.appdirect.model.ErrorCode;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by NENE on 2015-02-14.
 */
@XmlRootElement(name = "result")
public class AccountResponse {
    private boolean success;
    private ErrorCode errorCode;
    private String message;
}
