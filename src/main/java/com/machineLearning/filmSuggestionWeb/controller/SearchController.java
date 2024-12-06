package com.machineLearning.filmSuggestionWeb.controller;


import com.machineLearning.filmSuggestionWeb.dto.response.RestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/v1/suggestions/")
public class SuggestionController {
    @GetMapping()
    public ResponseEntity<RestResponse> getHelloWorld(){
        return ResponseEntity.ok().body(new RestResponse(200,"","","helloworld"));
    }
}
