package com.machineLearning.filmSuggestionWeb.controller;

import com.machineLearning.filmSuggestionWeb.dto.CreateCollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.CollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.service.CollectionFilmService;
import com.machineLearning.filmSuggestionWeb.exceptions.GeneralAllException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/collection-films")
public class CollectionFilmController {

    private final CollectionFilmService collectionFilmService;

    public CollectionFilmController(CollectionFilmService collectionFilmService) {
        this.collectionFilmService = collectionFilmService;
    }

    // Tạo CollectionFilm mới
    @PostMapping
    public ResponseEntity<String> createCollectionFilm(@RequestBody CreateCollectionFilmDTO createCollectionFilmDTO) {
        try {
            Boolean result = collectionFilmService.CreateCollectionFilms(createCollectionFilmDTO);
            if (result) {
                return new ResponseEntity<>("CollectionFilm created successfully.", HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("Failed to create CollectionFilm.", HttpStatus.BAD_REQUEST);
            }
        } catch (GeneralAllException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    // Lấy danh sách CollectionFilm theo userId và collectionId
    @GetMapping("/user/{userId}/collection/{collectionId}")
    public ResponseEntity<List<CollectionFilmDTO>> getCollectionFilmsByUserIdAndCollectionId(
            @PathVariable long userId, @PathVariable long collectionId) {
        try {
            List<CollectionFilmDTO> collectionFilms = collectionFilmService.GetAllBy_UserId_CollectionId(userId, collectionId);
            if (collectionFilms.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(collectionFilms, HttpStatus.OK);
        } catch (GeneralAllException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Xóa CollectionFilm theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCollectionFilmById(@PathVariable long id) {
        try {
            Boolean result = collectionFilmService.RemoveCollectionFilmsById(id);
            if (result) {
                return new ResponseEntity<>("Xóa phim thành công", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Xóa phim không thành công", HttpStatus.BAD_REQUEST);
            }
        } catch (GeneralAllException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/collection/{collectionId}")
    public ResponseEntity<String> removeCollectionFilmsByCollectionId(@PathVariable long collectionId) {
        try {
            Boolean result = collectionFilmService.RemoveCollectionFilmsByCollectionId(collectionId);
            if (result) {
                return new ResponseEntity<>("All CollectionFilms for the collection removed successfully.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Failed to remove CollectionFilms.", HttpStatus.BAD_REQUEST);
            }
        } catch (GeneralAllException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
