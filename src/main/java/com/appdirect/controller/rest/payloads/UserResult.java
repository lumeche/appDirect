package com.appdirect.controller.rest.payloads;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by Luis Tobon on 2015-02-16.
 */
@XmlRootElement(name = "result")
public class UserResult {
    private boolean success;

    public UserResult(boolean success) {
        this.success = success;
    }

    public UserResult() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
