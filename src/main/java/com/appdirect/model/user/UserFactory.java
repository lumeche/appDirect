package com.appdirect.model.user;

import com.appdirect.model.EventManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by Luis Tobon on 2015-02-16.
 */
@Component
public class UserFactory implements IUserFactory {

    @Autowired
    private EventManager eventManager;

    @Value("${appdirect.events.user.firstname}")
    private String firstNameXPath;
    @Value("${appdirect.events.user.lastname}")
    private String lastNameXPath;
    @Value("${appdirect.events.user.uuid}")
    private String userIdXPath;



    @Override
    public User buildUser(String event){
        String userFirstName=eventManager.getXpath(event,firstNameXPath);
        String userLastName=eventManager.getXpath(event,lastNameXPath);
        String userId = getUserId(event);
        return new User(userFirstName,userLastName,userId);
    }

    @Override
    public String getUserId(String event) {
        return eventManager.getXpath(event,userIdXPath);
    }

    @Override
    public User buildEmptyUser(String userId) {
        return new User(userId);
    }


    public void setEventManager(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    public void setFirstNameXPath(String firstNameXPath) {
        this.firstNameXPath = firstNameXPath;
    }

    public void setLastNameXPath(String lastNameXPath) {
        this.lastNameXPath = lastNameXPath;
    }

    public void setUserIdXPath(String userIdXPath) {
        this.userIdXPath = userIdXPath;
    }
}
