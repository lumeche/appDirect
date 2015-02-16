package com.appdirect.controller.rest.payloads;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@XmlRootElement(name = "result")
public class SubcriptionCreated {
    private String success;
    private String message;
    private String accountIdentifier;

    public SubcriptionCreated() {
    }

    public SubcriptionCreated(String success, String message, String accountIdentifier) {
        this.success = success;
        this.message = message;
        this.accountIdentifier = accountIdentifier;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
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
