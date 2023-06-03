package com.demo.todolist.Repository;

import com.demo.todolist.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
    User findByResetPasswordToken(String token);
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
