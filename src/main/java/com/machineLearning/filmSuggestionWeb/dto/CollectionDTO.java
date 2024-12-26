package com.machineLearning.filmSuggestionWeb.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class CollectionDTO {
    private Long id;
    @NotNull(message = "Tên bộ sưu tập không thể để trống!")
    private String name;
    private Long userId;
}
