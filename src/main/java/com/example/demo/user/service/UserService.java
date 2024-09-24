package com.example.demo.user.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.user.dao.UserMapper;
import com.example.demo.user.domain.UserRequestDTO;
import com.example.demo.user.domain.UserResponseDTO;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public UserResponseDTO login(UserRequestDTO params) {
        System.out.println("debug >>> UserService login");
        return userMapper.loginRow(params);
    }

    public void join(UserRequestDTO params, MultipartFile file) {
        System.out.println("debug >>> UserService join");
        System.out.println("debug >>> upload img : " + file.getOriginalFilename());

        String path = "C:\\Users\\82103\\kdt-workspace\\jsp_spring\\src\\main\\resources\\static\\resources\\img\\";

        UUID uuid = UUID.randomUUID();
        String fileName = uuid + "_" + file.getOriginalFilename();
        System.out.println("debug >>> upload uuid img : " + fileName);

        File saveFile = new File(path, fileName);
        try {
            file.transferTo(saveFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
        params.setImgUrl(fileName);
        userMapper.joinRow(params);
    }

}
