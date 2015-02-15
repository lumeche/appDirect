package com.appdirect.controller.rest.response;

import com.appdirect.model.ErrorCode;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by NENE on 2015-02-14.
 */
@XmlRootElement(name = "result")
public class SubcriptionResponse {
    private boolean success;
    private ErrorCode errorCode;
    private String message;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
