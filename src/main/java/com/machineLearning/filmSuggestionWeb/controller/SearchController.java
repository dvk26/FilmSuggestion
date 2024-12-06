package com.machineLearning.filmSuggestionWeb.controller;


import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import com.machineLearning.filmSuggestionWeb.service.FilmPosterService;
import com.machineLearning.filmSuggestionWeb.service.SearchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/searches/")
public class SearchController {

    private final SearchService searchService;
    private final FilmPosterService filmPosterService;

    public SearchController(SearchService searchService, FilmPosterService filmPosterService) {
        this.searchService = searchService;
        this.filmPosterService = filmPosterService;
    }
    @PostMapping("/{prompt}")
    public ResponseEntity<RestResponse> handlePrompt(@PathVariable String prompt) {
        RestResponse response = new RestResponse(201, "", " ", searchService.getResponseFromModel(prompt));
        return ResponseEntity.ok().body(response);
    }
}
