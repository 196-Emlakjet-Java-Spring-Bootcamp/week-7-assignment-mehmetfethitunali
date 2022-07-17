package com.emlakjet.finalproject.advertisement.controller;

import com.emlakjet.finalproject.advertisement.dto.AdvertisementDto;
import com.emlakjet.finalproject.advertisement.dto.UserDto;
import com.emlakjet.finalproject.advertisement.entity.Advertisement;
import com.emlakjet.finalproject.advertisement.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
@RequestMapping("/advertisements")
public class AdvertisementController {


    @Autowired
    private AdvertisementService advertisementService;

    @GetMapping("/{id}")
    public ResponseEntity<Advertisement> findById(@PathVariable Long id) {
        return new ResponseEntity(advertisementService.findAdvertisementById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Advertisement>> getAllOrderByCreatedAt() {
        return new ResponseEntity(advertisementService.getAllOrderByCreatedAt(), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Advertisement> createAdvertisement(@RequestHeader HttpHeaders headers, @RequestBody AdvertisementDto advertisementDto){
        String uri = "http://localhost:8081/users/me";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            ResponseEntity<UserDto> user = restTemplate.exchange(uri, HttpMethod.GET,entity, UserDto.class);
            if (advertisementService.createAdvertisement(advertisementDto, user.getBody())){
                return new ResponseEntity("Advertisement Created", HttpStatus.CREATED);
            } else {
                return new ResponseEntity("Not Created", HttpStatus.BAD_REQUEST);
            }
        } catch (HttpClientErrorException e){
            return new ResponseEntity("",HttpStatus.UNAUTHORIZED);
        }

    }
    @PutMapping("/{id}")
    public ResponseEntity<Advertisement> updateAdvertisement(@RequestHeader HttpHeaders headers,@PathVariable Long id, @RequestBody AdvertisementDto advertisementDto){
        String uri = "http://localhost:8081/users/me";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            ResponseEntity<UserDto> user = restTemplate.exchange(uri, HttpMethod.PUT, entity, UserDto.class);
            if (advertisementService.updateAdvertisement(id, advertisementDto, user.getBody().getId())){
                return new ResponseEntity("Advertisement Updated", HttpStatus.CREATED);
            } else {
                return new ResponseEntity("Not Updated", HttpStatus.BAD_REQUEST);
            }
        } catch (HttpClientErrorException e){
            return new ResponseEntity("", HttpStatus.UNAUTHORIZED);
        }

    }

    @GetMapping("/status")
    public ResponseEntity<List<Advertisement>> getAllByStatusPassive(@RequestHeader HttpHeaders headers){
        String uri = "http://localhost:8081/users/isAuthenticated";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            ResponseEntity<Boolean> isAuthenticated = restTemplate.exchange(uri,HttpMethod.GET,entity,Boolean.class);
            if(isAuthenticated.getBody()){
                return new ResponseEntity(advertisementService.getAllByStatusPassive(),HttpStatus.ACCEPTED);
            } else {
                return new ResponseEntity("",HttpStatus.UNAUTHORIZED);
            }
        } catch (HttpClientErrorException e){
            return new ResponseEntity("",HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<Advertisement> setStatusActive(@PathVariable Long id, @RequestHeader HttpHeaders headers){
        String uri = "http://localhost:8081/users/isAuthenticated";
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> entity = new HttpEntity<>("body", headers);
        try {
            ResponseEntity<Boolean> isAuthenticated = restTemplate.exchange(uri,HttpMethod.GET,entity,Boolean.class);
            if(isAuthenticated.getBody()){
                return new ResponseEntity(advertisementService.setStatusActive(id), HttpStatus.CREATED);
            } else {
                return new ResponseEntity("",HttpStatus.UNAUTHORIZED);
            }
        } catch (HttpClientErrorException e){
            return new ResponseEntity("",HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/findbyuser/{username}")
    public ResponseEntity<List<Advertisement>> findAllByUser(@PathVariable String username){
        return new ResponseEntity(advertisementService.findAllByUser(username),HttpStatus.OK);
    }

    @GetMapping("/findbypricebetween")
    public ResponseEntity<List<Advertisement>> findByPriceBetween(@RequestParam Long min, @RequestParam Long max){
        return new ResponseEntity(advertisementService.findByPriceBetween(min, max), HttpStatus.OK);
    }

    @GetMapping("/findbytext/{text}")
    public ResponseEntity<List<Advertisement>> findByTitleOrDetailedMessage(@PathVariable String text){
        return new ResponseEntity(advertisementService.findByTitleOrDetailedMessage(text),HttpStatus.OK);
    }

}
