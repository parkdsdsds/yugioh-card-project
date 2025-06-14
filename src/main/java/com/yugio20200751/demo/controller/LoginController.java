package com.yugio20200751.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }


    @GetMapping("/signup")
    public String signupPage() {
        return "signup"; // /resources/templates/signup.mustache 파일을 찾아 렌더링합니다.
    }
}
