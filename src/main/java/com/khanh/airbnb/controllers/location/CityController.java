package com.khanh.airbnb.controllers.location;

import com.khanh.airbnb.dto.location.CityResponseDto;
import com.khanh.airbnb.repositories.CityRepository;
import com.khanh.airbnb.services.CityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CityController {
    private final CityService cityService;


    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(path = "/cities")
    public ResponseEntity<List<CityResponseDto>> getAllCities() {
        List<CityResponseDto> cities = cityService.getAllCities();
        return new ResponseEntity<>(cities, HttpStatus.OK);
    }

}
