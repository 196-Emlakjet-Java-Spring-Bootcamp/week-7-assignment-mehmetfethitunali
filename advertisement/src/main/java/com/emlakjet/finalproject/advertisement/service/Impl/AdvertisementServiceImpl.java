package com.emlakjet.finalproject.advertisement.service.Impl;

import com.emlakjet.finalproject.advertisement.dao.AdvertisementRepository;
import com.emlakjet.finalproject.advertisement.dto.AdvertisementDto;
import com.emlakjet.finalproject.advertisement.dto.UserDto;
import com.emlakjet.finalproject.advertisement.entity.Advertisement;
import com.emlakjet.finalproject.advertisement.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

    @Autowired
    private AdvertisementRepository advertisementRepository;

    @Override
    public boolean createAdvertisement(AdvertisementDto advertisementDto, UserDto userDto) {
        try{
            Advertisement advertisement = new Advertisement();
            advertisement.setUserId(userDto.getId());
            advertisement.setTitle(advertisementDto.getTitle());
            advertisement.setDetailedMessage(advertisementDto.getDetailedMessage());
            advertisement.setPrice(advertisementDto.getPrice());
            advertisement.setCreatedAt(new Date());
            advertisement.setStatus("passive");
            advertisementRepository.save(advertisement);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean updateAdvertisement(Long id, AdvertisementDto advertisementDto, Long userId) {
        try {
            Optional<Advertisement> advertisement =  advertisementRepository.findById(id);
            if (advertisement.get().getUserId() != userId){
                return false;
            }
            advertisement.get().setTitle(advertisementDto.getTitle());
            advertisement.get().setDetailedMessage(advertisementDto.getDetailedMessage());
            advertisement.get().setPrice(advertisementDto.getPrice());
            advertisement.get().setUpdatedAt(new Date());
            advertisement.get().setStatus("passive");
            advertisementRepository.save(advertisement.get());
            return true;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public Advertisement findAdvertisementById(Long id){
        Advertisement advertisement = advertisementRepository.getById(id);
        Long view = advertisement.getView();
        advertisement.setView(view + 1);
        advertisementRepository.save(advertisement);
        return advertisementRepository.getById(id);
    }

    @Override
    public List<Advertisement> findAllByUser(String username) {
        Long id = advertisementRepository.getIdByUserName(username);
        return advertisementRepository.findAllByUser(id);
    }

    @Override
    public List<Advertisement> getAllOrderByCreatedAt() {
        return advertisementRepository.getAllOrderByCreatedAt();
    }

    @Override
    public List<Advertisement> findByPriceBetween(Long min, Long max) {
        return advertisementRepository.findByPriceBetween(min, max);
    }

    @Override
    public List<Advertisement> findByTitleOrDetailedMessage(String text) {
        return advertisementRepository.findByTitleOrDetailedMessageLike(text);
    }

    @Override
    public List<Advertisement> getAllByStatusPassive(){
        return advertisementRepository.getAllByStatusPassive();
    }

    @Override
    public boolean setStatusActive(Long id){
        try {
            Optional<Advertisement> advertisement = advertisementRepository.findById(id);
            advertisement.get().setStatus("active");
            advertisementRepository.save(advertisement.get());
            String uri = "http://localhost:8081/users/usersummary/" + advertisement.get().getUserId().toString();
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>("body");
            ResponseEntity<UserDto> userDtoResponseEntity = restTemplate.exchange(uri, HttpMethod.GET,entity,UserDto.class);

            AdvertisementDto advertisementDto = new AdvertisementDto();

            advertisementDto.setUsername(userDtoResponseEntity.getBody().getUsername());
            advertisementDto.setTitle(advertisement.get().getTitle());
            advertisementDto.setDetailedMessage(advertisement.get().getDetailedMessage());
            advertisementDto.setPrice(advertisement.get().getPrice());
            String uri2 = "http://localhost:8083/messages";
            HttpEntity<AdvertisementDto> advertisementDtoHttpEntity = new HttpEntity<>(advertisementDto);
            restTemplate.exchange(uri2,HttpMethod.POST,advertisementDtoHttpEntity,AdvertisementDto.class);
            return true;
        } catch (Exception e){
            return false;
        }
    }
}
