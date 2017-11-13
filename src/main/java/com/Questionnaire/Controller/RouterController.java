package com.Questionnaire.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RouterController {

	@RequestMapping("/")
    public String index() {
        return "index.html";
    }
	
	@RequestMapping(value="/content")
    public String content() {
        return "answer.html";
    }
	
	@RequestMapping("/dashboard")
    public String admin() {
        return "admin.html";
    }
	
	@RequestMapping("/w")
    public String warn() {
        return "warn.html";
    }
	
	@RequestMapping("/auth")
    public String login() {
        return "login.html";
    }
}
