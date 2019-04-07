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
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EncryptorComponent encryptorComponent;

    @PostMapping("/register")
    public Map register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.addUser(user);
        return Map.of("user", user);
    }

    @PostMapping("/login")
    public void login(@RequestBody User user, HttpServletResponse response) {
        Optional.ofNullable(userService.getUser(user.getUserName()))
                .or(() -> {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
                })
                .ifPresent(u -> {
                    if (!passwordEncoder.matches(user.getPassword(), u.getPassword())) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名或密码错误");
                    }
                    Map map = Map.of("uid", u.getId(), "aid", u.getAuthorityId());
                    // 生成加密token
                    String token = encryptorComponent.encrypt(map);
                    // 在header创建自定义的权限
                    response.setHeader("Authorization", token);
                });
    }

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
