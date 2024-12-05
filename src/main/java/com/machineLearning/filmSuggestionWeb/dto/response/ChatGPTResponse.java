package com.machineLearning.filmSuggestionWeb.dto.response;

import com.machineLearning.filmSuggestionWeb.dto.ChatGPTChoice;
import lombok.*;

import java.util.List;

@Data
public class ChatGPTResponse {
    private List<ChatGPTChoice> choices;
}
