package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.*;
import com.khanh.airbnb.dto.homestay.HomestayRequestDto;
import com.khanh.airbnb.dto.homestay.HomestayResponseDto;
import com.khanh.airbnb.exceptions.AccessDeniedException;
import com.khanh.airbnb.exceptions.ResourceNotFoundException;
import com.khanh.airbnb.mappers.HomestayMapper;
import com.khanh.airbnb.repositories.*;
import com.khanh.airbnb.services.impl.HomestayServiceImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.ResourceAccessException;

import javax.swing.text.html.Option;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class HomestayServiceTests {
    @Mock
    private HomestayRepository homestayRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private WardRepository wardRepository;

    @Mock
    private HomestayMapper homestayMapper;

    @Mock
    private HomestayAvailabilityRepository homestayAvailabilityRepository;

    @InjectMocks
    private HomestayServiceImp homestayService;

    private UserEntity user;
    private HomestayRequestDto request;
    private HomestayEntity homestay;
    private CityEntity city;
    private DistrictEntity district;
    private WardEntity ward;
    private HomestayResponseDto response;

    @BeforeEach
    void setUp() {
        user = new UserEntity();
        user.setUserId(1L);

        request = new HomestayRequestDto();
        request.setName("Test Homestay");
        request.setAddress("123 Main St");
        request.setDescription("Nice place");
        request.setPriceDefault(100);
        request.setMaxGuests(4);
        request.setCityId(1);
        request.setDistrictId(1);
        request.setWardId(1);
        request.setAvailableFrom(LocalDate.of(2025, 8, 1));
        request.setAvailableTo(LocalDate.of(2025, 8, 4));

        homestay = new HomestayEntity();
        homestay.setHomestayId(1L);
        homestay.setUserEntity(user);
        homestay.setName("Test Homestay");
        homestay.setPriceDefault(100);

        city = new CityEntity();
        city.setCityId(1);
        district = new DistrictEntity();
        district.setCityEntity(city);
        district.setDistrictId(1);
        ward = new WardEntity();
        ward.setDistrictEntity(district);
        ward.setWardId(1);

        response = new HomestayResponseDto();
        response.setHomestayId(1L);
    }

    @Test
    public void testCreateHomestaySuccess() {
        when(cityRepository.findById(1)).thenReturn(Optional.of(city));
        when(districtRepository.findById(1)).thenReturn(Optional.of(district));
        when(wardRepository.findById(1)).thenReturn(Optional.of(ward));
        when(homestayMapper.toDto(any(HomestayEntity.class))).thenReturn(response);

        HomestayResponseDto result = homestayService.createHomestay(user, request);

        assertEquals(1L, result.getHomestayId());
        verify(homestayRepository).save(any(HomestayEntity.class));
        verify(homestayAvailabilityRepository).saveAll(anyList());
    }

    @Test
    public void testGetHomestayByUser() {
        when(homestayRepository.findByUserEntity(user)).thenReturn(List.of(homestay));
        when(homestayMapper.toDto(homestay)).thenReturn(response);

        List<HomestayResponseDto> result = homestayService.getHomestayByUser(user);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getHomestayId());
    }

    @Test
    public void testGetHomestays() {
        when(homestayRepository.findAll()).thenReturn(List.of(homestay));
        when(homestayMapper.toDto(homestay)).thenReturn(response);

        List<HomestayResponseDto> result = homestayService.getHomestays();
        assertEquals(1, result.size());
    }

    @Test
    public void testGetHomestay() {
        when(homestayRepository.findById(1L)).thenReturn(Optional.of(homestay));
        when(homestayMapper.toDto(homestay)).thenReturn(response);
        HomestayResponseDto result = homestayService.getHomestay(1L);
        assertEquals(1L, result.getHomestayId());
    }

    @Test
    public void testGetHomestayNotFound() {
        when(homestayRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> homestayService.getHomestay(99L));
    }

    @Test
    public void testUpdateHomestaySuccess() {
        when(homestayRepository.findById(1L)).thenReturn(Optional.of(homestay));
        when(cityRepository.findById(1)).thenReturn(Optional.of(city));
        when(districtRepository.findById(1)).thenReturn(Optional.of(district));
        when(wardRepository.findById(1)).thenReturn(Optional.of(ward));
        when(homestayAvailabilityRepository.existsById(any())).thenReturn(false);
        when(homestayMapper.toDto(homestay)).thenReturn(response);

        HomestayResponseDto result = homestayService.updateHomestay(user, 1L, request);
        assertEquals(1L, result.getHomestayId());
        verify(homestayRepository).save(homestay);
        verify(homestayAvailabilityRepository, atLeastOnce()).save(any());
    }

    @Test
    public void testUpdateHomestayNotOwner() {
        UserEntity notOwner = new UserEntity();
        notOwner.setUserId(99L);
        when(homestayRepository.findById(1L)).thenReturn(Optional.of(homestay));

        assertThrows(AccessDeniedException.class, () -> homestayService.updateHomestay(notOwner, 1L, request));
    }

    @Test
    public void testUpdateHomestayInvalidDates() {
        request.setAvailableFrom(LocalDate.of(2025, 8, 10));
        request.setAvailableTo(LocalDate.of(2025, 8, 5));
        when(homestayRepository.findById(1L)).thenReturn(Optional.of(homestay));
        when(cityRepository.findById(request.getCityId())).thenReturn(Optional.of(city));
        when(districtRepository.findById(request.getDistrictId())).thenReturn(Optional.of(district));
        when(wardRepository.findById(request.getWardId())).thenReturn(Optional.of(ward));


        assertThrows(IllegalArgumentException.class, () -> homestayService.updateHomestay(user, 1L, request));
    }

}
