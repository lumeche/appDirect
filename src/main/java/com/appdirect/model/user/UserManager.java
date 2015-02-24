package com.appdirect.model.user;

import com.appdirect.model.subscription.Subscription;
import com.appdirect.model.subscription.SubscriptionManager;
import com.appdirect.model.utils.LoggerUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;
import org.openid4java.association.AssociationException;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.openid4java.consumer.VerificationResult;
import org.openid4java.discovery.DiscoveryException;
import org.openid4java.discovery.DiscoveryInformation;
import org.openid4java.discovery.Identifier;
import org.openid4java.message.AuthRequest;
import org.openid4java.message.MessageException;
import org.openid4java.message.ParameterList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * Created by Luis Tobon on 2015-02-15.
 */
@Component
public class UserManager {

    final static Logger logger = LoggerFactory.getLogger(UserManager.class);
    private static final String OPENID_DISC = "openid-disc";

    private Map<User, List<Subscription>> usersMap = Collections.synchronizedMap(new HashMap<User, List<Subscription>>());
    private Map<User, DiscoveryInformation> discoveryInformationMap = Collections.synchronizedMap(new HashMap<User, DiscoveryInformation>());
    @Autowired
    private SubscriptionManager subscriptionManager;

    @Autowired
    private IUserFactory userFactory;

    @Autowired
    private ConsumerManager consumerManager;

    public boolean authenticateUser(String userId, String idURL, String returnURL, HttpServletRequest request, ServletResponse response) {
        try {
            List discoveries = consumerManager.discover(idURL);
            DiscoveryInformation discovered = consumerManager.associate(discoveries);
            request.getSession().setAttribute(OPENID_DISC, discovered);
            User user = findUserByID(userId);
            discoveryInformationMap.put(user, discovered);
            AuthRequest authRequest = consumerManager.authenticate(discovered, returnURL);
            LoggerUtils.logDebug(logger, "About to redirect user %s to %s", userId, authRequest.getDestinationUrl(true));
//            response.sendRedirect(authRequest.getDestinationUrl(true));
            RequestDispatcher dispatcher = request.getRequestDispatcher("formredirection.jsp");
            request.setAttribute("parameterMap", request.getParameterMap());
            request.setAttribute("destinationUrl", authRequest.getDestinationUrl(false));
            dispatcher.forward(request, response);
        } catch (DiscoveryException | MessageException | ConsumerException | IOException | ServletException e) {
            LoggerUtils.logError(logger, e, "Error authenticating the user");
            return false;
        }
        return true;
    }


    public String verifyAuthentication(HttpServletRequest request) {
        ParameterList parameterList = new ParameterList(request.getParameterMap());
        DiscoveryInformation discovered = (DiscoveryInformation) request.getSession().getAttribute(OPENID_DISC);
        StringBuffer receivingURL = request.getRequestURL();
        String queryString = request.getQueryString();
        if (queryString != null && queryString.length() > 0)
            receivingURL.append("?").append(request.getQueryString());

        try {
            VerificationResult verification = consumerManager.verify(
                    receivingURL.toString(),
                    parameterList, discovered);
            Identifier verified = verification.getVerifiedId();
            if (verified != null){
                LoggerUtils.logDebug(logger,"Verification succed %s",verified.getIdentifier());
            }
            return  verified.getIdentifier();
        } catch (MessageException|DiscoveryException|AssociationException e) {
            LoggerUtils.logError(logger,e,"Error verifying authentication");
            return null;
        }
    }

    public boolean assignUser(String event) {
        User user = findUserByEvent(event);
        Subscription subscription = subscriptionManager.findSubscription(event);
        if (user == null || subscription == null) {
            LoggerUtils.logError(logger, "Either user or subscription is null");
            return false;
        }
        List<Subscription> subscriptionsForUser = usersMap.get(user);
        subscriptionsForUser.add(subscription);
        LoggerUtils.logDebug(logger, "User %s assigned to subscription %s", user.toString(), subscription.toString());
        return true;
    }

    public boolean unassignUser(String event) {
        User user = findUserByEvent(event);
        Subscription subscription = subscriptionManager.findSubscription(event);
        List<Subscription> subscriptions = usersMap.get(user);
        if (subscription == null || user == null) {
            LoggerUtils.logDebug(logger, "Either user or subscription is null");
            return false;
        }
        if (subscriptions.remove(subscription)) {
            LoggerUtils.logDebug(logger, "User %s unassigned from subscription %s", user.toString(), subscription.toString());
            return true;
        } else {
            LoggerUtils.logDebug(logger, "Subscription %s don't found for User %s", subscription.toString(), user.toString());
            return false;
        }
    }

    private User findUserByEvent(String event) {
        final String userId = userFactory.getUserId(event);
        if (!usersMap.containsKey(new User(userId))) {
            usersMap.put(userFactory.buildUser(event), new ArrayList<Subscription>());
        }
        return findUserByID(userId);
    }

    private User findUserByID(final String userId) {
        User user = CollectionUtils.find(usersMap.keySet(), new Predicate<User>() {
            @Override
            public boolean evaluate(User candidateUser) {
                return candidateUser.getUserId().equals(userId);
            }
        });
        if (user == null) {
            user = userFactory.buildEmptyUser(userId);
            usersMap.put(user, new ArrayList<Subscription>());
        }
        return user;
    }

    public Map<User, List<Subscription>> getSubscriptionForUser() {
        HashMap<User, List<Subscription>> newMap = new HashMap<User, List<Subscription>>();
        for (Map.Entry<User, List<Subscription>> entry : usersMap.entrySet()) {
            newMap.put(entry.getKey(), new ArrayList<Subscription>(entry.getValue()));
        }
        return newMap;
    }

    public void setSubscriptionManager(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }


    public void setUserFactory(UserFactory userFactory) {
        this.userFactory = userFactory;
    }

}
