package com.outsourcing.domain.user.controller;

import com.outsourcing.domain.user.model.request.CreateUserRequest;
import com.outsourcing.domain.user.model.response.CreateUserResponse;
import com.outsourcing.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<CreateUserResponse> handlerCreateUser(@Valid @RequestBody CreateUserRequest request) {

        CreateUserResponse result = userService.signup(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

}
