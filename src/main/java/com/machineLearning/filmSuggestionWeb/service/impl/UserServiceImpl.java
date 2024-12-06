package com.machineLearning.filmSuggestionWeb.service.impl;

import com.machineLearning.filmSuggestionWeb.config.MapperConfig;
import com.machineLearning.filmSuggestionWeb.dto.UserDTO;
import com.machineLearning.filmSuggestionWeb.exceptions.GeneralAllException;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.machineLearning.filmSuggestionWeb.repository.RoleRepository;
import com.machineLearning.filmSuggestionWeb.repository.UserRepository;
import com.machineLearning.filmSuggestionWeb.service.UserService;
import org.apache.catalina.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
  
    public UserServiceImpl(UserRepository userRepository, MapperConfig mapperConfig, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public void createUser(UserDTO userDTO) {
        UserEntity userEntity = modelMapper.map(userDTO,UserEntity.class);
        if(userRepository.existsByUserName(userDTO.getUserName()))
            throw new GeneralAllException("Username đã tồn tại!!");
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setRole(roleRepository.findByCode(userDTO.getRoleCode()));
        userDTO.setPassword(null);
        userRepository.save(userEntity);
    }
    @Override
    public void updateUser(UserDTO userDTO) {
        if(userDTO.getId()==null)
            throw  new GeneralAllException("Ko tồn tại người dùng!!");
        UserEntity userEntity= userRepository.findById(userDTO.getId()).
                orElseThrow(() -> new GeneralAllException("Khong tim thay nguoi dung co Id "+ userDTO.getId().toString()));
        //Update nguoi dung nhung ko duoc doi password
        String ollPassword= userEntity.getPassword();
        userEntity = modelMapper.map(userDTO,UserEntity.class);
        userEntity.setPassword(ollPassword);
        userEntity.setRole(roleRepository.findByCode(userDTO.getRoleCode()));
        userRepository.save(userEntity);
    }
    @Override
    public List<UserDTO> fetchAllUsers() {
        List<UserEntity> users= userRepository.findAll();
        List<UserDTO> res= new ArrayList<>();
        for (UserEntity user : users) {
            user.setPassword(null);
            res.add(modelMapper.map(user,UserDTO.class));
        }
        return res;
    }

    @Override
    public UserDTO findById(Long id) {
        if(id==null)
            throw  new GeneralAllException("Ko tồn tại người dùng!!");
        UserEntity userEntity= userRepository.findById(id).
                orElseThrow(() -> new GeneralAllException("Khong tim thay nguoi dung co Id "+ id.toString()));
        UserDTO res =modelMapper.map(userEntity, UserDTO.class);
        res.setPassword(null);
        return res;
    }

    @Override
    public UserEntity findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public void updateUserToken(String token, String userName)
    {
        UserEntity curentUser= userRepository.findByUserName(userName);
        if(curentUser!=null) {
            curentUser.setRefreshToken(token);
        }
        userRepository.save(curentUser);

    }

    @Override
    public UserEntity findByRefreshTokenAndUserName(String refreshToken, String userName) {
        return userRepository.findByRefreshTokenAndUserName(refreshToken,userName);
    }

}
