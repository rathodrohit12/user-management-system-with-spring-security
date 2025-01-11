package com.rohit.usermanagementsystem.utils;

import com.rohit.usermanagementsystem.dto.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class SessionHandler {

    public static void setSessionAttributes(HttpSession session, User user) {
        session.setAttribute("userId", user.getId());
        session.setAttribute("userName", user.getName());
        session.setAttribute("userMobile", user.getMobile());
        session.setAttribute("userEmail", user.getEmail());
        session.setAttribute("isAdmin", user.getRoles().contains("ROLE_ADMIN"));
    }
}
