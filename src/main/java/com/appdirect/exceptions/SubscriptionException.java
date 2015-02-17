package com.appdirect.exceptions;

/**
 * Created by Luis Tobon on 2015-02-16.
 */
public class SubscriptionException extends RuntimeException {
    public SubscriptionException() {
    }

    public SubscriptionException(String message) {
        super(message);
    }

    public SubscriptionException(String message, Throwable cause) {
        super(message, cause);
    }

    public SubscriptionException(Throwable cause) {
        super(cause);
    }

    public SubscriptionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
