package com.rohit.usermanagementsystem.service;

import com.rohit.usermanagementsystem.dto.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface OtpService {
    public User generateOTPAndSave(User user) throws UnsupportedEncodingException, MessagingException;
    public void sendOTPViaEmail(User user, String OTP) throws UnsupportedEncodingException, MessagingException;
    public void disposeOTP(User user);
    public boolean isOTPValid(User user);
}
