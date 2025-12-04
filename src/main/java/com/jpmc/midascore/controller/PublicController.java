package com.jpmc.midascore.controller;


import com.jpmc.midascore.entity.UserEntity;
import com.jpmc.midascore.service.MYUserDetailsService;
import com.jpmc.midascore.service.MailService;
import com.jpmc.midascore.service.UserService;
import com.jpmc.midascore.utils.JwtUtils;
import com.jpmc.midascore.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private MYUserDetailsService myUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    private final MailService mailService;
    private final UserService userService;

    public PublicController(MailService mailService, UserService userService) {
        this.mailService = mailService;
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserEntity userEntity) {
        UserEntity find = userService.findByUserName(userEntity.getUserName());
        if (find != null) {
            throw new ResourceAccessException("User with the name:" + find + " is exist");
        }
        if (!MailUtils.isValid(userEntity.getEmail())) {
            throw new RuntimeException("email is not valid");
        }
        UserEntity data = userService.saveNewUser(userEntity);
        boolean mailsent = mailService.signUpMail(userEntity.getEmail(), userEntity.getUserName());
        if (!mailsent) {

        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("User registered successfully. A confirmation email has been sent.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserEntity user) {
        try {
            // Authenticate user credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword())
            );
            UserDetails userDetails = myUserDetailsService.loadUserByUsername(user.getUserName());

            // Generate JWT token
            String jwt = jwtUtils.generateToken(userDetails.getUsername());

            // Fetch user info from DB
            UserEntity loggedInUser = userService.findByUserName(user.getUserName());

            // Prepare response with token and user info
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("username", loggedInUser.getUserName());
            response.put("email", loggedInUser.getEmail());
            response.put("message", "Login successful");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Incorrect username or password");
        }
    }
}
