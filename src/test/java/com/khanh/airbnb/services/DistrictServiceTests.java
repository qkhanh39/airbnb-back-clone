package com.khanh.airbnb.services;

import com.khanh.airbnb.domain.entities.CityEntity;
import com.khanh.airbnb.domain.entities.DistrictEntity;
import com.khanh.airbnb.dto.location.DistrictResponseDto;
import com.khanh.airbnb.mappers.DistrictMapper;
import com.khanh.airbnb.repositories.CityRepository;
import com.khanh.airbnb.repositories.DistrictRepository;
import com.khanh.airbnb.services.impl.DistrictServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DistrictServiceTests {
    @Mock
    private DistrictRepository districtRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private DistrictMapper districtMapper;

    @InjectMocks
    private DistrictServiceImpl districtService;

    @Test
    public void testThatGetDistrictsByCityIdReturnsDistricts() {
        CityEntity testCity = CityEntity.builder().cityId(1).name("test city").build();
        DistrictEntity testDistrictA = DistrictEntity.builder().districtId(1).cityEntity(testCity).name("test district A").build();
        DistrictEntity testDistrictB = DistrictEntity.builder().districtId(2).cityEntity(testCity).name("test district B").build();

        List<DistrictEntity> districts = List.of(testDistrictA, testDistrictB);

        when(cityRepository.findById(testCity.getCityId())).thenReturn(Optional.of(testCity));
        when(districtRepository.findByCityEntity(testCity)).thenReturn(districts);

        DistrictResponseDto testDtoA = DistrictResponseDto.builder().districtId(1).name("test district A").build();
        DistrictResponseDto testDtoB = DistrictResponseDto.builder().districtId(2).name("test district B").build();
        when(districtMapper.toDto(testDistrictA)).thenReturn(testDtoA);
        when(districtMapper.toDto(testDistrictB)).thenReturn(testDtoB);

        List<DistrictResponseDto> results = districtService.findByCityId(1);

        assertEquals(2, results.size());
        assertEquals("test district A", results.get(0).getName());
        assertEquals("test district B", results.get(1).getName());

        verify(cityRepository).findById(1);
        verify(districtMapper).toDto(testDistrictA);
        verify(districtMapper).toDto(testDistrictB);
    }
}
