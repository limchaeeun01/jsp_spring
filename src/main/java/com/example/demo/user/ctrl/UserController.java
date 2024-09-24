package com.example.demo.user.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.FieldError;
import org.springframework.ui.Model;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.user.domain.UserRequestDTO;
import com.example.demo.user.domain.UserResponseDTO;
import com.example.demo.user.service.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login.multicampus")
    public String login(UserRequestDTO params,
            HttpSession session,
            RedirectAttributes attributes) {

        System.out.println("debug >>> endpoint : /user/index.multicampus");
        System.out.println("debug >>> params : " + params);
        UserResponseDTO result = userService.login(params);
        System.out.println("debug >>> result : " + result);

        if (result != null) {
            String userPwd = params.getPwd();
            String encoderPwd = result.getPwd();

            if (passwordEncoder.matches(userPwd, encoderPwd)) {
                System.out.println("debug >>> matches() true");
                result.setPwd("");
                session.setAttribute("loginUser", result);
                return "landing";
            } else {
                attributes.addFlashAttribute("loginFail", "비밀번호가 일치하지 않습니다.");
                return "redirect:/";
            }

        } else {
            return "redirect:/";
        }

    }

    @GetMapping("/logout.multicampus")
    public String logout(HttpSession session) {
        System.out.println("debug >>> endpoint : /user/logout.multicampus");

        session.invalidate();

        return "redirect:/";
    }

    @GetMapping("/join.multicampus")
    public String joinForm(HttpSession session) {
        System.out.println("debug >>> endpoint : /join.multicampus");

        return "join";
    }

    @PostMapping("/join.multicampus")
    public String join(@Valid UserRequestDTO params,
            BindingResult bindingResult,
            Model model) {

        System.out.println("debug >>> endpoint : /join.multicampus");
        System.out.println("debug >>> params : " + params);

        if (bindingResult.hasErrors()) {
            System.out.println("debug >>> vaild has errors");
            List<ObjectError> list = bindingResult.getAllErrors();
            Map<String, String> map = new HashMap<>();
            for (int idx = 0; idx < list.size(); idx++) {
                FieldError field = (FieldError) list.get(idx);
                String key = field.getField();
                String msg = field.getDefaultMessage();
                System.out.println(key + "\t" + msg);
                model.addAttribute(key, msg);
            }
            return "join";
        } else {
            System.out.println("debug >>> 유효성 검증 통과");
            System.out.println("debug >>> 암호화 객체 = " + passwordEncoder);
            System.out.println("debug >>> params = " + params);
            String encoderPwd = passwordEncoder.encode(params.getPwd());
            System.out.println("debug >>> encoderPwd = " + encoderPwd);
            params.setPwd(encoderPwd);
            userService.join(params);

            return "redirect:/";
        }
    }

}
