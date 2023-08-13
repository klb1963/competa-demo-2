package com.competa.competademo.service;

import com.competa.competademo.dto.CreateUserDto;
import com.competa.competademo.dto.UserDto;
import com.competa.competademo.entity.ImageInfo;
import com.competa.competademo.entity.User;

import java.util.List;


public interface UserService {


    User saveUser(CreateUserDto userDto);

    User addUserRole(long userId, String roleName);

    User removeUserRole(long userId, String roleName);

    boolean isUserByEmailExist(String email);

    User findById(long id);

    List<UserDto> findAllUsers();

    User saveUser(User user);

    User findByEmail(String userEmail);

    User getAuthUser();

    void addAvatar(ImageInfo avatar);

    String getAvatar(User user);
}
