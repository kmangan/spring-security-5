package com.kieranmangan.auth.controller;

import com.google.common.collect.ImmutableMap;
import com.kieranmangan.auth.model.UserForm;
import com.kieranmangan.auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.UUID;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/user")
    public ResponseEntity createUser(@Valid @RequestBody UserForm form) {
        UUID userId = userService.save(form);
        return ResponseEntity.status(HttpStatus.CREATED).body(ImmutableMap.of("id", userId));
    }

}
