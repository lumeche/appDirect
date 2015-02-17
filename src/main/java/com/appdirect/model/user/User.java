package com.appdirect.model.user;

import org.springframework.stereotype.Component;

/**
 * Created by Luis Tobon on 2015-02-16.
 */
@Component
public class User {
    private String firstName;
    private String lastName;
    private String userId;

    public User() {
    }


    public User(String userId) {
        this.userId=userId;
    }

    public User(String firstName, String lastName, String userId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return String.format("User id[%s] firstName[%s] lastName[%s]",userId,firstName,lastName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!userId.equals(user.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
