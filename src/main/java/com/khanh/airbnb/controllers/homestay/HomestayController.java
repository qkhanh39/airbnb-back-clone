package com.khanh.airbnb.controllers.homestay;

import com.khanh.airbnb.domain.entities.UserEntity;
import com.khanh.airbnb.dto.homestay.HomestayRequestDto;
import com.khanh.airbnb.dto.homestay.HomestayResponseDto;
import com.khanh.airbnb.services.HomestayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HomestayController {

    private final HomestayService homestayService;

    public HomestayController(HomestayService homestayService) {
        this.homestayService = homestayService;
    }

    @PostMapping(path = "/me/homestays")
    public ResponseEntity<HomestayResponseDto> createHomestay(@AuthenticationPrincipal UserEntity user,
                                                              @RequestBody HomestayRequestDto request) {
        HomestayResponseDto responseDto = homestayService.createHomestay(user, request);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping(path = "/me/homestays")
    public ResponseEntity<List<HomestayResponseDto>> getOwnerHomestays(@AuthenticationPrincipal UserEntity user) {
        List<HomestayResponseDto> homestays = homestayService.getHomestayByUser(user);
        return new ResponseEntity<>(homestays, HttpStatus.OK);
    }

    @GetMapping(path = "/homestays")
    public ResponseEntity<List<HomestayResponseDto>> getHomestays() {
        List<HomestayResponseDto> homestays = homestayService.getHomestays();
        return  new ResponseEntity<>(homestays, HttpStatus.OK);
    }

    @GetMapping(path = "/homestays/{homestayId}")
    public ResponseEntity<HomestayResponseDto> getHomestay(@PathVariable("homestayId") Long homestayId) {
        HomestayResponseDto homestay = homestayService.getHomestay(homestayId);
        return new ResponseEntity<>(homestay, HttpStatus.OK);
    }

    @PatchMapping(path = "/me/homestays/{homestayId}")
    public ResponseEntity<HomestayResponseDto> updateHomestay(@AuthenticationPrincipal UserEntity user,
                                                              @PathVariable("homestayId") Long homestayId,
                                                              @RequestBody HomestayRequestDto request) {
        HomestayResponseDto updatedHomestay = homestayService.updateHomestay(user, homestayId, request);
        return new ResponseEntity<>(updatedHomestay, HttpStatus.OK);
    }
}
