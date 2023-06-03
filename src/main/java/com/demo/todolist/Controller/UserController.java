package com.demo.todolist.Controller;

import com.demo.todolist.Dtos.DtoAuthRespuesta;
import com.demo.todolist.Dtos.UserDTO;
import com.demo.todolist.Entity.User;
import com.demo.todolist.Repository.UserRepo;
import com.demo.todolist.Security.CustomUsersDetailsService;
import com.demo.todolist.Security.JwtGenerador;
import com.demo.todolist.Service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth/")
@CrossOrigin("http://localhost:5173")
public class UserController {

    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;

    private UserServiceImpl impl;

    private JwtGenerador jwtGenerador;


    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";

    @Autowired
    public UserController(AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, UserServiceImpl impl, JwtGenerador jwtGenerador) {
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.impl = impl;
        this.jwtGenerador = jwtGenerador;
    }

    //Método para poder registrar usuarios con role "user"
    @PostMapping("register")
    public ResponseEntity<?> registrar(@RequestBody User user) {
        if (impl.existUser(user.getUsername())) {
            return new ResponseEntity<>("el usuario ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
        User usuarios = new User();
        usuarios.setUsername(user.getUsername());
        usuarios.setEmail(user.getEmail());
        usuarios.setPassword(passwordEncoder.encode(user.getPassword()));
        impl.createUser(usuarios);

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generarToken(authentication);

        return new ResponseEntity<>(new DtoAuthRespuesta(token), HttpStatus.OK);
    }


//    Método para poder logear un usuario y obtener un token
    @PostMapping("login")
    public ResponseEntity<DtoAuthRespuesta> login(@RequestBody User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtGenerador.generarToken(authentication);

        return new ResponseEntity<>(new DtoAuthRespuesta(token), HttpStatus.OK);
    }

    @GetMapping("getMe")
    public ResponseEntity<UserDTO> getMe(@RequestHeader(value = "Authorization") String token){
        String username = jwtGenerador.obtenerUsernameDeJwt(token.substring(7));
        Optional<User> user = impl.getUser(username);

        if(user.isPresent()){
            UserDTO userDTO = new UserDTO(user.get().getId(), user.get().getUsername(), user.get().getEmail(), user.get().getTasks());
            return ResponseEntity.ok(userDTO);
        }
        return ResponseEntity.notFound().build();

    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validateToken(@RequestHeader(value = "Authorization") String token){
        Boolean isAuth = jwtGenerador.validarToken(token.substring(7));
        if(isAuth){
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    };

}