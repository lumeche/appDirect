package com.appdirect.controller.response;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by NENE on 2015-02-14.
 */
@XmlRootElement
public class AccountResponse {
    private boolean success;
    private String message;
    private String accountIdentifier;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAccountIdentifier() {
        return accountIdentifier;
    }

    public void setAccountIdentifier(String accountIdentifier) {
        this.accountIdentifier = accountIdentifier;
    }
}
