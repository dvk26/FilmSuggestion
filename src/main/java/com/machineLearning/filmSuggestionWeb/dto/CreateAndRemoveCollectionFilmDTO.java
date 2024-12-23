package com.machineLearning.filmSuggestionWeb.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAndRemoveCollectionFilmDTO {
    private List<CreateCollectionFilmDTO> createList;
    private List<CreateCollectionFilmDTO> removeList;
}
