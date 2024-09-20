package com.example.demo.user.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.user.domain.UserRequestDTO;
import com.example.demo.user.domain.UserResponseDTO;
import com.example.demo.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login.multicampus")
    public String login(UserRequestDTO params, HttpSession session) {

        System.out.println("debug >>> endpoint : /user/index.multicampus");
        System.out.println("debug >>> params : " + params);
        UserResponseDTO result = userService.login(params);
        System.out.println("debug >>> result : " + result);

        if (result != null) {
            session.setAttribute("loginUser", result);
            return "landing";
        } else {
            return "redirect:";
        }

    }
}
