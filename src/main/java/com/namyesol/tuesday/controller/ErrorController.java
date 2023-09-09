package com.namyesol.tuesday.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {
    
    @RequestMapping("/error/{statusCode}")
    public String errorPage(@PathVariable String statusCode) {

        if (statusCode.startsWith("5")) {
            statusCode = "500";
        }
        
        return "error/" + statusCode;
    }
}
