package com.machineLearning.filmSuggestionWeb.controller;

import com.machineLearning.filmSuggestionWeb.dto.CollectionDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import com.machineLearning.filmSuggestionWeb.service.CollectionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/collections")
@Validated
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    // Tạo bộ sưu tập mới
    @PostMapping
    public ResponseEntity<RestResponse> createCollection(@Valid @RequestBody CollectionDTO collectionDTO) {
        collectionService.createCollection(collectionDTO);
        return ResponseEntity.status(200).body(new RestResponse(200, "", "Tạo bộ sưu tập thành công!", collectionDTO));
    }

    // Cập nhật bộ sưu tập
    @PutMapping("/{id}")
    public ResponseEntity<RestResponse> updateCollection(@PathVariable Long id, @Valid @RequestBody CollectionDTO collectionDTO) {
        collectionDTO.setId(id);
        collectionService.updateCollection(collectionDTO);
        return ResponseEntity.status(200).body(new RestResponse(200, "", "Cập nhật bộ sưu tập thành công!", collectionDTO));
    }

    // Xóa bộ sưu tập
    @DeleteMapping("/{id}")
    public ResponseEntity<RestResponse> deleteCollection(@PathVariable Long id) {
        collectionService.deleteCollection(id);
        return ResponseEntity.status(200).body(new RestResponse(200, "", "Xóa bộ sưu tập thành công!", null));
    }

    // Lấy tất cả bộ sưu tập của người dùng
    @GetMapping("/user/{userId}")
    public ResponseEntity<RestResponse> fetchAllCollectionsByUserId(@PathVariable Long userId) {
        List<CollectionDTO> collections = collectionService.fetchAllCollectionsByUserId(userId);
        return ResponseEntity.status(200).body(new RestResponse(200, "", "Danh sách bộ sưu tập của người dùng!", collections));
    }
}
