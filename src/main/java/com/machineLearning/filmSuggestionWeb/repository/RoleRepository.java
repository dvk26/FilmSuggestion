package com.machineLearning.filmSuggestionWeb.repository;

import com.machineLearning.filmSuggestionWeb.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {

    public boolean existsByCode(String code);
    public RoleEntity findByCode(String code);
}
