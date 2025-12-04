package com.jpmc.midascore.service;


import com.jpmc.midascore.entity.UserEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MYUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public MYUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userService.findByUserName(username);
        if (user == null){
            throw  new UsernameNotFoundException("User not found with the name of: " + username);
        }
        return User.builder()
                .username(user.getUserName())
                .password(user.getPassword())
                .build();
    }
}
