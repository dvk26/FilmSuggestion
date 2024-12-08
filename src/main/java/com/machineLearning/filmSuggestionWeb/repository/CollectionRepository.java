package com.machineLearning.filmSuggestionWeb.repository;

import com.machineLearning.filmSuggestionWeb.model.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {
    
    // Tìm bộ sưu tập theo userId
    List<CollectionEntity> findByUserId(Long userId);
    
    // Kiểm tra xem bộ sưu tập có tồn tại hay không
    boolean existsById(Long id);
    
    // Xóa bộ sưu tập theo id
    void deleteById(Long id);
}
