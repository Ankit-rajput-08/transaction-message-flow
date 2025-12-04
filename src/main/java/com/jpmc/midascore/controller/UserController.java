package com.jpmc.midascore.controller;


import com.jpmc.midascore.entity.UserEntity;
import com.jpmc.midascore.exception.ResourceNotFoundException;
import com.jpmc.midascore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUser(){
        List<UserEntity> all = userService.findAllUser();
        if (all != null){
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        UserEntity get = userService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with the id: " + id) );

        if (get != null){
            return new ResponseEntity<>(get, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        boolean remove = userService.deleteById(id);
        if (remove){
            return new ResponseEntity<>(remove,HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateById(@RequestBody UserEntity newEntry,
                                        @PathVariable Long id){
        UserEntity oldEntry = userService.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User not found with the id: " + id));
        if (oldEntry != null){
            oldEntry.setUserName(newEntry.getUserName() != null && !newEntry.getUserName().equals(" ")? newEntry.getUserName() : oldEntry.getUserName());
            oldEntry.setEmail(newEntry.getEmail() != null && !newEntry.getEmail().equals(" ")? newEntry.getEmail() : oldEntry.getEmail());
            oldEntry.setPassword(newEntry.getPassword() != null && !newEntry.getPassword().equals(" ")? newEntry.getPassword() : oldEntry.getPassword());
            userService.save(oldEntry);
            return new ResponseEntity<>(oldEntry,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
