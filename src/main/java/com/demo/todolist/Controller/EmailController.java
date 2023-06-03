package com.demo.todolist.Controller;

import com.demo.todolist.Entity.User;
import com.demo.todolist.Repository.UserRepo;
import com.demo.todolist.Service.EmailService;
import com.demo.todolist.Service.UserServiceImpl;
import com.demo.todolist.Util.RequestNewPassword;
import com.demo.todolist.Util.RequestPasswordReset;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

@RestController
public class EmailController {

    @Autowired
    private EmailService service;

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/api/reset-password")
    public ResponseEntity<?> sendEmail(@RequestBody RequestPasswordReset requestPasswordReset) throws Exception {
        Optional<User> userDB = userService.getUser(requestPasswordReset.getUsername());

        String token = service.createRandomToken();

        if(userDB.isPresent()){
            User user = userDB.get();
            userService.updateResetPasswordToken(token, user.getUsername());
            service.sendMail(user.getEmail(), "Restablece tu contraseña", token);
            return ResponseEntity.ok("email enviado " + requestPasswordReset.getUsername());
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay ningun email con ese usuario");
    }

    @PostMapping("/api/reset-password/validation/{token}")
    public ResponseEntity<?> resetPasswordTokenValidation(@PathVariable String token) throws Exception {

        User user = userService.getByResetPasswordToken(token);

        boolean isValid = userService.existUser(user.getUsername());

        return ResponseEntity.ok(isValid);
    }

    @PostMapping("/api/reset-password/{token}")
    public ResponseEntity<?> resetPassword(@PathVariable String token, @RequestBody RequestNewPassword newPassword) throws Exception {
        User user = userService.getByResetPasswordToken(token);

        userService.updatePassword(user, newPassword.getPassword());

        return ResponseEntity.ok(user);
    }
}
