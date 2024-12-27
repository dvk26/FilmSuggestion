package com.machineLearning.filmSuggestionWeb.controller;


import com.machineLearning.filmSuggestionWeb.dto.CreateAndRemoveCollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import com.machineLearning.filmSuggestionWeb.service.FilmService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/films")
@Validated
public class FilmController {
    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }
    @PostMapping("/{filmId}/likes")
    public ResponseEntity<RestResponse> likeFilm(@PathVariable(name="filmId") Long id){
        filmService.likeFilm(id);
        return ResponseEntity.ok().body(new RestResponse(200,"","",null));
    }
    @PostMapping("/{filmId}/disLikes")
    public ResponseEntity<RestResponse> disLikeFilm(@PathVariable(name="filmId") Long id){
        filmService.disLikeFilm(id);
        return ResponseEntity.ok().body(new RestResponse(200,"","",null));
    }
}
