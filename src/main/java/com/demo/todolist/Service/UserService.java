package com.demo.todolist.Service;

import com.demo.todolist.Entity.User;

import java.util.Optional;

public interface UserService{
    Optional<User> getUser(String username);
    Boolean existUser(String username);
    User createUser(User user);
    User updatePassword(User user, String newPassword);
    void updateResetPasswordToken(String token, String username);
    User getByResetPasswordToken(String token);

}
