package com.example.springbootexamples.controller;

import com.example.springbootexamples.component.EncryptorComponent;
import com.example.springbootexamples.entity.Address;
import com.example.springbootexamples.entity.User;
import com.example.springbootexamples.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/{uid}/addresses")
    public Map postAddress(@RequestBody Address address, @RequestAttribute int uid) {
        address.setUser(new User(uid));
        userService.addAddress(address);
        return Map.of("address", address);
    }

    @GetMapping("/users/{uid}/addresses")
    public Map getAddresses(@RequestAttribute int uid) {
        return Map.of("addresses", userService.listAddresses(uid));
    }

    @PatchMapping("/users/{uid}/addresses/{aid}")
    public Map patchAddress(@RequestBody Address address) {
        return Map.of("address", userService.updateAddress(address));
    }
}
