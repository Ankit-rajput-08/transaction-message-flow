package com.jpmc.midascore.service;


import com.jpmc.midascore.entity.UserEntity;
import com.jpmc.midascore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


    public UserEntity saveNewUser(UserEntity userEntity){
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        return userRepository.save(userEntity);
    }

    public void saveAdmin(UserEntity userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userRepository.save(userEntry);
    }

    public UserEntity save(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    public List<UserEntity> findAllUser(){
        return userRepository.findAll();
    }
    public Optional<UserEntity> findById(Long id){
        return userRepository.findById(id);
    }

    public boolean deleteById(Long id){
        if (userRepository.existsById(id)){
            userRepository.deleteById(id);
        }
        else {
            throw new RuntimeException("not found with id: " + id);
        }
        return true;
    }
    public UserEntity findByUserName(String userName){
        return userRepository.findByUserName(userName);
    }
}

