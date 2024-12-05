package com.machineLearning.filmSuggestionWeb.service.impl;

import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.machineLearning.filmSuggestionWeb.repository.FilmRepository;
import com.machineLearning.filmSuggestionWeb.repository.UserRepository;
import com.machineLearning.filmSuggestionWeb.service.FilmService;
import com.machineLearning.filmSuggestionWeb.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class FilmServiceImpl implements FilmService {

    private final FilmRepository filmRepository;

    private final UserRepository userRepository;

    public FilmServiceImpl(SecurityUtil securityUtil, FilmRepository filmRepository, UserRepository userRepository) {

        this.filmRepository = filmRepository;
        this.userRepository = userRepository;
    }


    @Override
    public FilmEntity saveFilm(Map<String, Object> film) {
        FilmEntity filmEntity = new FilmEntity();
        filmEntity.setTitle((String) film.get("title"));
        int year = (int) film.get("year");
        filmEntity.setYear((long) year);
        filmEntity.setImdbRating((double) film.get("imdb_rating"));
        filmEntity.setOverview((String) film.get("overview"));
        List<String> genreList = (List<String>) film.get("genres");
        String genres= genreList.stream().collect(Collectors.joining());
        filmEntity.setGenres(genres);

        String userName = SecurityUtil.getCurrentUserLogin().isPresent()?
                SecurityUtil.getCurrentUserLogin().get(): "";
        UserEntity userLogin= userRepository.findByUserName(userName);
        if(filmRepository.existsByTitleAndUser_Id(filmEntity.getTitle(),userLogin.getId())){
            filmEntity.setUser(userLogin);
            filmRepository.save(filmEntity);
            filmRepository.flush();
            return filmEntity;
        }
        return filmRepository.findByTitleAndUser_Id(filmEntity.getTitle(),userLogin.getId());
    }
}
