package com.machineLearning.filmSuggestionWeb.controller;



import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.service.SearchService;
import com.machineLearning.filmSuggestionWeb.util.ResponseToJsonUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;


@RestController
@RequestMapping(value = "/api/v1/search")
public class SearchController {

    private final SearchService searchService;

    public SearchController(SearchService searchService) {
        this.searchService = searchService;
    }
    @PostMapping("/{prompt}")
    public ResponseEntity<RestResponse> handlePrompt(@PathVariable String prompt) {
        RestResponse response = new RestResponse(201, "", " ", searchService.getResponseFromModel(prompt));
        return ResponseEntity.ok().body(response);
    }
}
