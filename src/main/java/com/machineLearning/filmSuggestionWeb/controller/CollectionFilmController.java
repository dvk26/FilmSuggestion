package com.machineLearning.filmSuggestionWeb.controller;

import com.machineLearning.filmSuggestionWeb.dto.CollectionDTO;
import com.machineLearning.filmSuggestionWeb.dto.CreateCollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.CreateAndRemoveCollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.CollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.FilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import com.machineLearning.filmSuggestionWeb.service.CollectionFilmService;


import com.machineLearning.filmSuggestionWeb.exceptions.GeneralAllException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Console;
import java.util.List;

@RestController
@RequestMapping("/api/collectionfilms")
public class CollectionFilmController {

    private final CollectionFilmService collectionFilmService;

    @Autowired
    public CollectionFilmController(CollectionFilmService collectionFilmService) {
        this.collectionFilmService = collectionFilmService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createCollectionFilm(
            @RequestBody CreateCollectionFilmDTO createCollectionFilmDTO) {
        try {

            Boolean isCreated = collectionFilmService.CreateCollectionFilms(createCollectionFilmDTO);
            return ResponseEntity.status(200).body("Collection Film created successfully.");
        } catch (GeneralAllException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/CreateAndRemove")
    public ResponseEntity<String> createAndRemoveCollectionFilm(
            @RequestBody CreateAndRemoveCollectionFilmDTO createAndRemoveCollectionFilmDTO) {
        try {
            Boolean check = collectionFilmService.CreateAndRemoveCollectionFilm_By_CollectionId_FilmId(createAndRemoveCollectionFilmDTO);
            if (check == true) {
                return ResponseEntity.status(200).body("Create and Remove Collection Film Successfully");
            } else {
                return ResponseEntity.badRequest().body("Can't Create and Remove Collection Film Correctly ");
            }
        } catch (GeneralAllException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API to get all collection films by user ID and collection ID
    @GetMapping("/get/{userId}/{collectionId}")
    public ResponseEntity<RestResponse> getAllByUserIdAndCollectionId(
            @PathVariable long userId,
            @PathVariable long collectionId) {
        return ResponseEntity.ok().body(new RestResponse(201, "", " ", collectionFilmService.GetAllBy_UserId_CollectionId(userId,collectionId)));
    }
    @GetMapping("/tickedCollection/{filmId}")
    public ResponseEntity<RestResponse> getCollectionByFilmId(@PathVariable Long filmId){
        return ResponseEntity.ok().body(new RestResponse(201, "", " ", collectionFilmService.fetchAllCollectionTicked(filmId)));
    }
    // API to remove a collection film by ID
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<String> removeCollectionFilmById(@PathVariable long id) {
        try {
            Boolean isRemoved = collectionFilmService.RemoveCollectionFilmsById(id);
            return ResponseEntity.ok("Collection Film removed successfully.");
        } catch (GeneralAllException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // API to remove all collection films by collection ID
    @DeleteMapping("/removeByCollection/{collectionId}")
    public ResponseEntity<String> removeCollectionFilmsByCollectionId(@PathVariable long collectionId) {
        try {
            Boolean isRemoved = collectionFilmService.RemoveCollectionFilmsByCollectionId(collectionId);
            return ResponseEntity.ok("All Collection Films in the collection removed successfully.");
        } catch (GeneralAllException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
