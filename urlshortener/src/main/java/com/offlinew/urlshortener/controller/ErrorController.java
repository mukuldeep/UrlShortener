package com.offlinew.urlshortener.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {

    @GetMapping("/expired")
    public String showExpiredPage() {
        return "expired";
    }

    @GetMapping("/not_found")
    public String showNotFoundPage() {
        return "notFoundUrl";
    }

}
