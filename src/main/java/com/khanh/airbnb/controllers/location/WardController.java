package com.khanh.airbnb.controllers.location;

import com.khanh.airbnb.dto.location.WardResponseDto;
import com.khanh.airbnb.services.WardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class WardController {
    private WardService wardService;

    public WardController (WardService wardService){
        this.wardService = wardService;
    }

    @GetMapping(path = "/districts/{districtId}/wards")
    public ResponseEntity<List<WardResponseDto>> getWardsOfDistrict(@PathVariable("districtId") Integer districtId) {
        List<WardResponseDto> wards = wardService.findByDistrictId(districtId);
        return new ResponseEntity<>(wards, HttpStatus.OK);
    }
}
