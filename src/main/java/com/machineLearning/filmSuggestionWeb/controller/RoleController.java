package com.machineLearning.filmSuggestionWeb.controller;


import com.machineLearning.filmSuggestionWeb.dto.RoleDTO;
import com.machineLearning.filmSuggestionWeb.dto.UserDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import com.machineLearning.filmSuggestionWeb.service.RoleService;
import com.machineLearning.filmSuggestionWeb.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/roles")
@Validated
public class RoleController {
    private final RoleService roleService;
    private final ModelMapper modelMapper;

    public RoleController(RoleService roleService, ModelMapper modelMapper) {
        this.roleService = roleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity<RestResponse> createRole(@Valid @RequestBody RoleDTO roleDTO){
        roleService.saveRole(roleDTO);
        return ResponseEntity.status(200).body(new RestResponse(200,""," Tạo mới người dùng thành công!",roleDTO));
    }
}
