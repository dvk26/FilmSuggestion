package com.machineLearning.filmSuggestionWeb.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TMDBResponse {
    private int page;
    private List<TMDBMovie> results;
    private int total_pages;
    private int total_results;
}
