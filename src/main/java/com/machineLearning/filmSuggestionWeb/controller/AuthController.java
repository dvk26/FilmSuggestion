package com.machineLearning.filmSuggestionWeb.controller;

import com.machineLearning.filmSuggestionWeb.dto.UserDTO;
import com.machineLearning.filmSuggestionWeb.dto.request.LoginDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.ResLoginDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.machineLearning.filmSuggestionWeb.repository.UserRepository;
import com.machineLearning.filmSuggestionWeb.service.UserService;
import com.machineLearning.filmSuggestionWeb.util.SecurityUtil;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthController {

    @Value("${booksuggestion.jwt.refresh-token-validity-in-seconds}")
    public Long refreshExpiration;
    private final SecurityUtil securityUtil;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final UserService userService;
    private final ModelMapper modelMapper;

    public AuthController(SecurityUtil securityUtil, AuthenticationManagerBuilder authenticationManagerBuilder, UserRepository userRepository, ModelMapper modelMapper, UserService userService, ModelMapper modelMapper1) {
        this.securityUtil = securityUtil;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userService = userService;
        this.modelMapper = modelMapper1;
    }

    @PostMapping("login")
    public ResponseEntity<RestResponse> login( @Valid  @RequestBody LoginDTO loginDTO){
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginDTO.getUserName(), loginDTO.getPassword());

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);


        String accessToken = this.securityUtil.createAccessToken(authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userService.findByUserName(loginDTO.getUserName());
        ResLoginDTO res= new ResLoginDTO();
        UserDTO userInfo= modelMapper.map(user, UserDTO.class);
        userInfo.setPassword(null);
        res.setUser(userInfo);
        res.setAccessToken(accessToken);

        String refreshToken = securityUtil.createRefreshToken(loginDTO.getUserName(),res);
        userService.updateUserToken(refreshToken,loginDTO.getUserName());

        ResponseCookie responseCookie=ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshExpiration)
                .build();
        return ResponseEntity.status(200)
                .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
                .body(new RestResponse(200,"","Đăng nhập thành công!",res));
    }
}
