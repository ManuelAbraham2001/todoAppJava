package com.demo.todolist.Service;

import com.demo.todolist.Entity.User;
import com.demo.todolist.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepo repo;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public Optional<User> getUser(String username) {
        return repo.findByUsername(username);
    }

    @Override
    public Boolean existUser(String username) {
        return repo.existsByUsername(username);
    }

    @Override
    public User createUser(User user) {
        return repo.save(user);
    }

    @Override
    public User updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setForgotPasswordToken(null);
        return repo.save(user);
    }

    @Override
    public void updateResetPasswordToken(String token, String username) throws UsernameNotFoundException{
        Optional<User> userBD = repo.findByUsername(username);
        if(userBD.isPresent()){
            User user = userBD.get();
            user.setForgotPasswordToken(token);
            repo.save(user);
        }else{
            throw new UsernameNotFoundException("No se encontro el user");
        }
    }

    @Override
    public User getByResetPasswordToken(String token) {
        return repo.findByResetPasswordToken(token);
    }


}
