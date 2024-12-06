package com.machineLearning.filmSuggestionWeb.repository;

import com.machineLearning.filmSuggestionWeb.model.HistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<HistoryEntity, Long>{
    
    public boolean existsByPromptAndUser_Id(String prompt, Long userId);
    public HistoryEntity findByPromptAndUser_Id(String prompt, Long userId);
}
