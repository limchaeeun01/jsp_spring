package com.example.demo.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.servlet.http.HttpSession;


@Controller
public class IndexController {

    @GetMapping("/")
    public String index(HttpSession session) {
        System.out.println("debug >>> endpoint : /index.multicampus");
        
        if(session.getAttribute("loginUser") != null){
            return "landing";
        }

        return "index";
    }
}
