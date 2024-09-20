package com.example.demo.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @GetMapping("/index.multicampus")
    public String index() {
        System.out.println("debug >>> endpoint : /index.multicampus");
        return "index";
    }
}
