package com.machineLearning.filmSuggestionWeb.service.impl;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.machineLearning.filmSuggestionWeb.dto.response.TMDBResponse;
import com.machineLearning.filmSuggestionWeb.service.FilmPosterService;
import com.machineLearning.filmSuggestionWeb.util.PreprocessTitleUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;


@Service
public class FilmPosterServiceImpl implements FilmPosterService {


    private String urlMovie="https://api.themoviedb.org/3/search/movie";
    private String urlTv = "https://api.themoviedb.org/3/search/tv";

    private String apiKey="eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyMjg3ZjA4ZDgyY2QyOTgwMzAxMGU2MWMxZWM3NDZkMyIsIm5iZiI6MTczMzIzODUzNC41NDIsInN1YiI6IjY3NGYxZjA2Njc5ZWMxNTMyODgyNmM3ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.3Wr6yyXdSas_NHbdeS_Xsf6sBps_6FanemHseejIIms";
    private final PreprocessTitleUtil preprocessTitleUtil;

    public FilmPosterServiceImpl(PreprocessTitleUtil preprocessTitleUtil) {
        this.preprocessTitleUtil = preprocessTitleUtil;
    }

    @Override
    public String getPosterFromTitleAndYear(String title, Long year) {
        String processedTitle= preprocessTitleUtil.preprocessTitle(title);
        WebClient webClientMovie = WebClient.builder()
                .baseUrl(urlMovie)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        String responseMovie = webClientMovie.get()
                .uri(uriBuilder -> uriBuilder .path("")
                        .queryParam("query", processedTitle)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        WebClient webClientTv = WebClient.builder()
                .baseUrl(urlTv)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();

        String responseTv = webClientTv.get()
                .uri(uriBuilder -> uriBuilder .path("")
                        .queryParam("query", processedTitle)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block();
        try {
            StringBuilder sb = new StringBuilder("https://image.tmdb.org/t/p/w500");
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(JsonParser.Feature.ALLOW_COMMENTS);
            TMDBResponse tmdbMovieResponse = mapper.readValue(responseMovie, new TypeReference<TMDBResponse>() {});
            TMDBResponse tmdbTvResponse = mapper.readValue(responseTv, new TypeReference<TMDBResponse>() {});
            if(tmdbTvResponse.getResults().size()>0){
                return sb.append(tmdbTvResponse.getResults().get(0).getPoster_path()).toString();
            }
            if(tmdbMovieResponse.getResults().size()>0){
                return sb.append(tmdbMovieResponse.getResults().get(0).getPoster_path()).toString();
            }

            System.out.println(tmdbTvResponse.getResults().get(0).getPoster_path());
            return sb.append(tmdbTvResponse.getResults().get(0).getPoster_path()).toString();
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
