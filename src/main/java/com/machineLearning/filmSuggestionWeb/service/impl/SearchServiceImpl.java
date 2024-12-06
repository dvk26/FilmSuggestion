package com.machineLearning.filmSuggestionWeb.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.model.HistoryEntity;
import com.machineLearning.filmSuggestionWeb.model.HistoryFilmEntity;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.machineLearning.filmSuggestionWeb.service.FilmService;
import com.machineLearning.filmSuggestionWeb.service.HistoryFilmService;
import com.machineLearning.filmSuggestionWeb.service.HistoryService;
import com.machineLearning.filmSuggestionWeb.service.SearchService;
import com.machineLearning.filmSuggestionWeb.repository.HistoryRepository;
import com.machineLearning.filmSuggestionWeb.repository.UserRepository;
import com.machineLearning.filmSuggestionWeb.util.ResponseToJsonUtil;
import com.machineLearning.filmSuggestionWeb.util.SecurityUtil;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {


    String url ="https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=AIzaSyBm1xgORlBqy3TzlKuS70ckKW74AVig-gk";

    private final ResponseToJsonUtil responseToJsonUtil;
    private final FilmService filmService;
    private final HistoryService historyService;
    private final UserRepository userRepository;

    private final HistoryFilmService historyFilmService;


    public SearchServiceImpl(ResponseToJsonUtil responseToJsonUtil, FilmService filmService, HistoryService historyService, UserRepository userRepository, HistoryFilmService historyFilmService) {
        this.responseToJsonUtil = responseToJsonUtil;
        this.filmService = filmService;
        this.historyService = historyService;



        this.userRepository = userRepository;
        this.historyFilmService = historyFilmService;
    }


    @Override
    public List<FilmEntity> getResponseFromModel(String prompt) {
        WebClient webClient = WebClient.create();
        String response = webClient.post()
                .uri(url)
                .header("Content-Type", "application/json")
                .bodyValue(String.format("""
                {
                  "contents": [
                    {
                      "parts": [
                        {
                          "text": "Gợi ý khoảng 7-8 bộ phim, nếu có nhiều hơn, thì chọn ra 7-8 bộ có điểm số IMDB cao nhất.
                           - Tên phim (title)
                           - Những thể loại của phim (genres) - trả về một list
                           - Năm sản xuất (year) - trả về số nguyên
                           - Điểm số IMDB (imdb_rating) - trả về số thực dương
                           - Thời lượng phim (runtime): trả về số nguyên, đơn vị là phút
                           - Tóm tắt nội dung của phim (overview): dễ tả mạch lạc và đầy đủ nội dung phim, không quá 1000 chữ.
                           - Nội dung trả về dưới dạng json
                           {\\\"title\\\": \\\"phim 1\\\",
                           \\\"genres\\\": [\\\"Kinh dị\\\", \\\"Viễn tưởng\\\", ...],
                           \\\"year\\\": 2020,
                           \\\"imdb_rating\\\": \\\"...\\\",
                           \\\"runtime\\\": ...,
                           \\\"overview\\\": \\\"....\\\"}
                           - Trong đó overview: Nội dung giới thiệu tổng quát về câu hỏi của người dùng để biết đang gợi ý những phim gì (khoảng 30 chữ, câu văn phải mạch lạc).
                           - Dùng danh xưng Miuvie thay cho tôi hoặc chúng tôi.
                           - Đây là yêu cầu của người dùng: \\\"%s\\\""
                        }
                      ]
                    }
                  ]
                }
                """, prompt)) // Replace 'prompt' with the actual variable containing the user input
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String json= responseToJsonUtil.convertResponseToJson(response);

        String userName = SecurityUtil.getCurrentUserLogin().isPresent()?
                SecurityUtil.getCurrentUserLogin().get(): "";
        UserEntity userLogin= userRepository.findByUserName(userName);

        if (historyService.existsByPromptAndUser_Id(prompt, userLogin.getId())) {
           return historyFilmService.getListFilmSearched(historyService.findByPromptAndUser_Id(prompt, userLogin.getId()));
        }

        List<FilmEntity> films = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> movies = mapper.readValue(json, new TypeReference<>() {});
            for (Map<String, Object> movie : movies) {
                films.add(filmService.saveFilm(movie));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Save the history and films
        historyFilmService.saveListFilmSearched(historyService.save(prompt, films), films);
        return films;
    }


}
