package com.appdirect.model.user;

/**
 * Created by Luis Tobon on 2015-02-19.
 */
public interface IUserFactory {
    User buildUser(String event);

    String getUserId(String event);

    User buildEmptyUser(String userId);
}
