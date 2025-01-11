package com.rohit.usermanagementsystem.utils;

import org.springframework.ui.Model;

public class ModelHandler {

    public static void prepareModel(Model model, String message, String msgType, boolean showOtpModal) {
        model.addAttribute("msg", message);
        model.addAttribute("msgType", msgType);
        model.addAttribute("showOtpModal", showOtpModal);
    }

    public static void prepareModelWithObject(Model model, String message, String msgType,
                                              String objectName, Object object, boolean otpValid) {
        model.addAttribute("msg", message);
        model.addAttribute("msgType", msgType);
        model.addAttribute(objectName, object);
        model.addAttribute("otpValid", otpValid);
    }
}
