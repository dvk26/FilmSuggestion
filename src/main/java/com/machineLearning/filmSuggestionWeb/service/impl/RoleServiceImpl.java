package com.machineLearning.filmSuggestionWeb.service.impl;

import com.machineLearning.filmSuggestionWeb.dto.RoleDTO;
import com.machineLearning.filmSuggestionWeb.exceptions.GeneralAllException;
import com.machineLearning.filmSuggestionWeb.model.RoleEntity;
import com.machineLearning.filmSuggestionWeb.repository.RoleRepository;
import com.machineLearning.filmSuggestionWeb.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }
    @Override
    public void saveRole(RoleDTO roleDTO) {
        if(roleRepository.existsByCode(roleDTO.getCode()))
            throw new GeneralAllException("Role đã tồn tại!");
        roleRepository.save(modelMapper.map(roleDTO, RoleEntity.class));
    }
}