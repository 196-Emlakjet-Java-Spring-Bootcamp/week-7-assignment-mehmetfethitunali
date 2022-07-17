package com.emlakjet.finalproject.advertisement.service;

import com.emlakjet.finalproject.advertisement.dto.AdvertisementDto;
import com.emlakjet.finalproject.advertisement.dto.UserDto;
import com.emlakjet.finalproject.advertisement.entity.Advertisement;
import java.util.List;

public interface AdvertisementService {

    boolean createAdvertisement(AdvertisementDto advertisementDto, UserDto userDto);

    boolean updateAdvertisement(Long id, AdvertisementDto advertisementDto, Long userId);

    boolean setStatusActive(Long id);

    List<Advertisement> findAllByUser(String username);

    List<Advertisement> getAllOrderByCreatedAt();

    List<Advertisement> findByPriceBetween(Long min, Long max);

    List<Advertisement> findByTitleOrDetailedMessage(String text);

    Advertisement findAdvertisementById(Long id);

    List<Advertisement> getAllByStatusPassive();


}
