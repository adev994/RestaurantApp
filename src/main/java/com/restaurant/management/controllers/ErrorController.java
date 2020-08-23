package com.restaurant.management.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError() {
        return "error-404";
    }


}
