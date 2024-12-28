package com.machineLearning.filmSuggestionWeb.service.impl;

import com.machineLearning.filmSuggestionWeb.config.MapperConfig;
import com.machineLearning.filmSuggestionWeb.dto.CreateCollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.CreateAndRemoveCollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.CollectionDTO;
import com.machineLearning.filmSuggestionWeb.dto.CollectionFilmDTO;
import com.machineLearning.filmSuggestionWeb.dto.response.FilmDTO;
import com.machineLearning.filmSuggestionWeb.exceptions.GeneralAllException;
import com.machineLearning.filmSuggestionWeb.model.CollectionEntity;
import com.machineLearning.filmSuggestionWeb.model.CollectionFilmEntity;
import com.machineLearning.filmSuggestionWeb.model.FilmEntity;
import com.machineLearning.filmSuggestionWeb.model.UserEntity;
import com.machineLearning.filmSuggestionWeb.repository.CollectionFilmRepository;
import com.machineLearning.filmSuggestionWeb.repository.FilmRepository;
import com.machineLearning.filmSuggestionWeb.repository.UserRepository;
import com.machineLearning.filmSuggestionWeb.repository.CollectionRepository;
import com.machineLearning.filmSuggestionWeb.service.CollectionFilmService;
import com.machineLearning.filmSuggestionWeb.util.SecurityUtil;
import org.modelmapper.Converters;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class CollectionFilmServiceImpl implements CollectionFilmService {
    private final CollectionFilmRepository collectionfilmRepository;
    private final FilmRepository filmRepository;
    private final ModelMapper modelMapper;
    private final CollectionFilmRepository collectionFilmRepository;

    private final CollectionRepository collectionRepository;
    private final UserRepository userRepository;

    public CollectionFilmServiceImpl(CollectionRepository collectionRepository,
                                     CollectionFilmRepository collectionfilmRepository,
                                     FilmRepository filmRepository,
                                     MapperConfig mapperConfig,
                                     ModelMapper modelMapper, CollectionFilmRepository collectionFilmRepository, CollectionRepository collectionRepository1, SecurityUtil securityUtil, UserRepository userRepository) {

        this.collectionfilmRepository = collectionfilmRepository;
        this.filmRepository = filmRepository;
        this.modelMapper = modelMapper;
        this.collectionFilmRepository = collectionFilmRepository;
        this.collectionRepository = collectionRepository1;

        this.userRepository = userRepository;
    }

    @Override
    public Boolean CreateCollectionFilms(CreateCollectionFilmDTO createCollectionFilmDTO) {
        if (createCollectionFilmDTO.getFilm_id() == null || createCollectionFilmDTO.getCollection_id() == null) {
            throw new GeneralAllException("Film ID và Collection ID không được bỏ trống.");
        }

        CollectionFilmEntity temp = new CollectionFilmEntity();

        FilmEntity film = filmRepository.findById(createCollectionFilmDTO.getFilm_id())
                .orElseThrow(() -> new GeneralAllException(
                        "Không tìm thấy film với ID " + createCollectionFilmDTO.getFilm_id()));
        temp.setFilm(film);

        CollectionEntity collection = collectionRepository.findById(createCollectionFilmDTO.getCollection_id())
                .orElseThrow(() -> new GeneralAllException(
                        "Không tìm thấy collection với ID " + createCollectionFilmDTO.getCollection_id()));
        temp.setCollection(collection);

        temp.setDate_add(new java.sql.Date(System.currentTimeMillis()));

        collectionfilmRepository.save(temp);
        return true;
    }

    @Override
    public List<FilmDTO> GetAllBy_UserId_CollectionId(long userId, long collectionId) {
        List<CollectionFilmEntity> entityList = collectionfilmRepository.findByCollectionIdAndUserId(userId, collectionId);
        List<FilmEntity> filmEntities = entityList.stream().map(s->s.getFilm()).toList();
        return filmEntities.stream().map(s->{
            FilmDTO filmDTO = modelMapper.map(s,FilmDTO.class);
            filmDTO.setUserId(userId);
            return filmDTO;
        }).toList();
    }
    

    @Override
    public Boolean RemoveCollectionFilmsById(long Id) {
        if (!collectionfilmRepository.existsById(Id)) {
            throw new GeneralAllException("Không tồn tại collection film với ID " + Id);
        }

        collectionfilmRepository.deleteById(Id);
        return true;
    }

    @Override
    public Boolean RemoveCollectionFilmsByCollectionId(long CollectionId) {
        if (!collectionRepository.existsById(CollectionId)) {
            throw new GeneralAllException("Không tồn tại collection với ID " + CollectionId);
        }

        List<CollectionFilmEntity> temp = collectionfilmRepository.findByCollection_Id(CollectionId);
        for (CollectionFilmEntity cf : temp) {
            RemoveCollectionFilmsById(cf.getId());
        }

        return true;
    }

    @Override
    public Boolean CreateAndRemoveCollectionFilm_By_CollectionId_FilmId(CreateAndRemoveCollectionFilmDTO _createAndRemoveCollectionFilm)
    {
        Boolean flag = false;
        List<Long> collectionfilmsid = collectionFilmRepository.findAllByFilmId(_createAndRemoveCollectionFilm.getFilmId());
        for(int i = 0; i < collectionfilmsid.size(); i++)
        {
            RemoveCollectionFilmsById(collectionfilmsid.get(i));
        }

        for(int i = 0; i < _createAndRemoveCollectionFilm.getAddCollections().size(); i++)
        {
            flag = true;
            CreateCollectionFilmDTO dto = new CreateCollectionFilmDTO();
            dto.setCollection_id(_createAndRemoveCollectionFilm.getAddCollections().get(i));
            dto.setFilm_id(_createAndRemoveCollectionFilm.getFilmId());
            CreateCollectionFilms(dto);
        }

        return flag;
    }

    @Override
    public List<CollectionDTO> fetchAllCollectionTicked(Long filmId) {
        String userName = SecurityUtil.getCurrentUserLogin().isPresent()?
                SecurityUtil.getCurrentUserLogin().get(): "";

        UserEntity userEntity= userRepository.findByUserName(userName);
        if(userEntity == null){
            throw  new GeneralAllException("Ko ton tai nguoi dung!");
        }
        List<Long> collectionIds= collectionFilmRepository.findAllByCollectionIdByFilmIdAndUserId(filmId,userEntity.getId());
        List<CollectionEntity> collectionEntities = collectionRepository.findAllById(collectionIds);
        List<CollectionDTO> res =new ArrayList<>();
        collectionEntities.stream().forEach(s->{
            CollectionDTO collectionDTO = modelMapper.map(s,CollectionDTO.class);
            collectionDTO.setUserId(userEntity.getId());
            res.add(collectionDTO);
        });
        return res;
    }

}