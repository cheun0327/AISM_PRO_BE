package com.upvote.aismpro.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionListener implements HttpSessionListener {

    @Override
    public void sessionCreated(HttpSessionEvent event) {
        event.getSession().setMaxInactiveInterval(6*60*60);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent event) {

        HttpSession session = event.getSession();

        String userID = (String) session.getAttribute("userId");

        if (userID != null) {
            // Session 초기화
            session.invalidate();
        }
    }

}
