package com.machineLearning.filmSuggestionWeb.controller;

import com.machineLearning.filmSuggestionWeb.dto.UserDTO;
import com.machineLearning.filmSuggestionWeb.dto.request.LoginDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.ResLoginDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import com.machineLearning.filmSuggestionWeb.exceptions.GeneralAllException;
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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

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


        String accessToken = this.securityUtil.createAccessToken(authentication,null,false);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserEntity user = userService.findByUserName(loginDTO.getUserName());
        ResLoginDTO res= new ResLoginDTO();
        UserDTO userInfo= modelMapper.map(user, UserDTO.class);
        userInfo.setPassword(null);
        res.setUser(userInfo);
        res.setAccessToken(accessToken);

        String refreshToken = securityUtil.createRefreshToken(loginDTO.getUserName(),userInfo);
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

    @GetMapping("account")
    public ResponseEntity<RestResponse> getAccount(){
        String userName = SecurityUtil.getCurrentUserLogin().isPresent()?
                SecurityUtil.getCurrentUserLogin().get(): "";
        UserEntity userEntity = userService.findByUserName(userName);
        UserDTO userDTO = modelMapper.map(userEntity,UserDTO.class);
        userDTO.setPassword(null);

        return ResponseEntity.status(200)
                .body(new RestResponse(200,"","Lay thong tin thanh cong",userDTO));
    }

    @PostMapping("register")
    public ResponseEntity<RestResponse> register( @Valid  @RequestBody UserDTO userDTO){

        userDTO.setRoleCode("USER");
        userService.createUser(userDTO);
        return ResponseEntity.status(200)
                .body(new RestResponse(200,"","Đăng kí thành công!",userDTO));
    }

    @GetMapping("refresh")
    public ResponseEntity<RestResponse> getRefreshToken(
            @CookieValue(name="refresh_token")String refresh_token){
        Jwt decodedToken=securityUtil.checkValidateRefreshToken(refresh_token);
        String userName = decodedToken.getSubject();
        UserEntity currentUser= userService.findByRefreshTokenAndUserName(refresh_token,userName);
        if(currentUser==null){
            throw new GeneralAllException("Refresh Token không phù hợp!");
        }

        UserEntity currentUserDB= userService.findByUserName(userName);
        UserDTO currentUserDTO = modelMapper.map(currentUserDB,UserDTO.class);
        currentUserDTO.setRoleCode(currentUserDB.getRole().getCode());
        currentUserDTO.setPassword(null);


        String accessToken= securityUtil.createAccessToken(null,currentUserDTO,true);
        String newRefreshToken= securityUtil.createRefreshToken(userName, currentUserDTO);
        userService.updateUserToken(newRefreshToken,userName);

        ResLoginDTO resLoginDTO = new ResLoginDTO();
        resLoginDTO.setAccessToken(accessToken);
        resLoginDTO.setUser(currentUserDTO);


        ResponseCookie responseCookie=ResponseCookie
                .from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshExpiration)
                .build();

        return ResponseEntity.status(200)
                .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
                .body(new RestResponse(200,"","Đăng nhập thành công!",resLoginDTO));
    }

    @PostMapping("logout")
    public ResponseEntity logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.updateUserToken(null,authentication.getName());
        ResponseCookie deleteSpringCookie=ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .body("log out thanh cong");
    }

}
