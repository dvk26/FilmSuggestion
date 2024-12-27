package com.machineLearning.filmSuggestionWeb.dto;


import lombok.Getter;
import lombok.Setter;
import java.util.List;
@Getter
@Setter
public class CreateAndRemoveCollectionFilmDTO {
    private Long filmId;
    private List<Long> addCollections;
}
