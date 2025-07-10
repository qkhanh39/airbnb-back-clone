package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.CityEntity;
import com.khanh.airbnb.dto.location.CityResponseDto;
import com.khanh.airbnb.mappers.CityMapper;
import com.khanh.airbnb.repositories.CityRepository;
import com.khanh.airbnb.services.impl.CityServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CityServiceTests {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @InjectMocks
    private CityServiceImpl cityService;

    @Test
    public void testThatGetAllCitiesReturnsCities() {
        CityEntity testCityA = CityEntity.builder().name("test city A").build();
        CityEntity testCityB = CityEntity.builder().name("test city B").build();

        when(cityRepository.findAll()).thenReturn(List.of(testCityA, testCityB));

        CityResponseDto testDtoA = CityResponseDto.builder().name("test city A").build();
        CityResponseDto testDtoB = CityResponseDto.builder().name("test city B").build();

        when(cityMapper.toDto(testCityA)).thenReturn(testDtoA);
        when(cityMapper.toDto(testCityB)).thenReturn(testDtoB);

        List<CityResponseDto> results = cityService.getAllCities();

        assertEquals(2, results.size());
        assertEquals("test city A", results.get(0).getName());
        assertEquals("test city B", results.get(1).getName());

        verify(cityRepository).findAll();
        verify(cityMapper).toDto(testCityA);
        verify(cityMapper).toDto(testCityB);
    }
}
