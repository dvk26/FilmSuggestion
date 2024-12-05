package com.machineLearning.filmSuggestionWeb.controller;


import com.machineLearning.filmSuggestionWeb.dto.UserDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import com.machineLearning.filmSuggestionWeb.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users")
@Validated
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<RestResponse> createUser(@Valid @RequestBody UserDTO userDTO){

        userService.createUser(userDTO);
        System.out.println("hello world");
        return ResponseEntity.status(200).body(new RestResponse(200,""," Tạo mới người dùng thành công!",userDTO));
    }
    @PutMapping
    public ResponseEntity<RestResponse> updateUser(@Valid @RequestBody UserDTO userDTO){
        userService.updateUser(userDTO);
        return ResponseEntity.status(201).body(new RestResponse(201,""," Cập nhập người dùng thành công!",userDTO));
    }
    @GetMapping
    public ResponseEntity<RestResponse> fetchAllUsers() {
        return ResponseEntity.status(200).body(new RestResponse(200, "", " Danh sách tất cả người dùng!", userService.fetchAllUsers()));
    }
    @GetMapping("/{id}")
    public ResponseEntity<RestResponse> findUserById() {
        return ResponseEntity.status(200).body(new RestResponse(200, "", " Danh sách tất cả người dùng!", userService.fetchAllUsers()));
    }


}
