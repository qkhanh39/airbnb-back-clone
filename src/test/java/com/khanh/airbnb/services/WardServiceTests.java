package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.CityEntity;
import com.khanh.airbnb.domain.entities.DistrictEntity;
import com.khanh.airbnb.domain.entities.WardEntity;
import com.khanh.airbnb.dto.location.WardResponseDto;
import com.khanh.airbnb.exceptions.ResourceNotFoundException;
import com.khanh.airbnb.mappers.WardMapper;
import com.khanh.airbnb.repositories.DistrictRepository;
import com.khanh.airbnb.repositories.WardRepository;
import com.khanh.airbnb.services.impl.WardServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WardServiceTests {
    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private WardRepository wardRepository;

    @Mock
    private WardMapper wardMapper;

    @InjectMocks
    private WardServiceImpl wardService;

    @Test
    public void testThatGetAllWardsReturnsWards() {
        CityEntity city = new CityEntity();
        city.setCityId(1);
        city.setName("testCity");

        DistrictEntity district = new DistrictEntity();
        district.setDistrictId(1);
        district.setName("test district");
        district.setCityEntity(city);

        WardEntity ward = new WardEntity();
        ward.setWardId(1);
        ward.setName("test ward");
        ward.setDistrictEntity(district);

        WardResponseDto response = new WardResponseDto(1, "test ward");

        when(districtRepository.findById(district.getDistrictId())).thenReturn(Optional.of(district));
        when(wardRepository.findByDistrictEntity(district)).thenReturn(List.of(ward));
        when(wardMapper.toDto(ward)).thenReturn(response);

        List<WardResponseDto> result = wardService.findByDistrictId(district.getDistrictId());

        assertEquals(1, result.size());
        assertEquals("test ward", result.get(0).getName());

        verify(districtRepository).findById(district.getDistrictId());
        verify(wardRepository).findByDistrictEntity(district);
        verify(wardMapper).toDto(ward);
    }

    @Test
    public void testThatNotFoundDistrictThrowsResourceNotFoundException() {
        when(districtRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            wardService.findByDistrictId(999);
        });

        verify(districtRepository).findById(999);
        verifyNoMoreInteractions(wardRepository, wardMapper);
    }
}
