package com.khanh.airbnb.controllers.location;

import com.khanh.airbnb.dto.location.DistrictResponseDto;
import com.khanh.airbnb.services.DistrictService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class DistrictController {
    private DistrictService districtService;

    public DistrictController(DistrictService districtService) {
        this.districtService = districtService;
    }

    @GetMapping(path = "/cities/{cityId}/districts")
    public ResponseEntity<List<DistrictResponseDto>> getDistrictsOfCity(@PathVariable("cityId") Integer cityId) {
        List<DistrictResponseDto> districts = districtService.findByCityId(cityId);
        return new ResponseEntity<>(districts, HttpStatus.OK);
    }
}
