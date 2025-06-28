package com.khanh.airbnb.mappers;

import com.khanh.airbnb.domain.entities.BookingEntity;
import com.khanh.airbnb.dto.booking.BookingRequestDto;
import com.khanh.airbnb.dto.booking.BookingResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingEntity toEntity(BookingRequestDto bookingRequestDto);

    @Mapping(source = "homestayEntity.homestayId", target = "homestayId")
    @Mapping(source = "homestayEntity.name", target = "homestayName")
    BookingResponseDto toDto(BookingEntity bookingEntity);
}
