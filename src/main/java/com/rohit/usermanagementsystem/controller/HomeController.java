package com.rohit.usermanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String showHomepage() {
        return "index";
    }


    @GetMapping("/about")
    public String showAboutPage() {
        return "about";
    }

    @GetMapping("/contact")
    public String showContactPage() {
        return "contact";
    }
}
