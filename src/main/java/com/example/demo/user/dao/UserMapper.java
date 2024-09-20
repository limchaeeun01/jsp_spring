package com.example.demo.user.dao;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.user.domain.UserResponseDTO;
import com.example.demo.user.domain.UserRequestDTO;

@Mapper
public interface UserMapper {
    
    public UserResponseDTO loginRow(UserRequestDTO params);
}
