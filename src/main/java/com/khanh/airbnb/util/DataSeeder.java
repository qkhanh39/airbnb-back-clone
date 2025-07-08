package com.khanh.airbnb.util;

import com.khanh.airbnb.domain.entities.CityEntity;
import com.khanh.airbnb.domain.entities.DistrictEntity;
import com.khanh.airbnb.domain.entities.WardEntity;
import com.khanh.airbnb.repositories.CityRepository;
import com.khanh.airbnb.repositories.DistrictRepository;
import com.khanh.airbnb.repositories.HomestayRepository;
import com.khanh.airbnb.repositories.WardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private CityRepository cityRepository;
    private DistrictRepository districtRepository;
    private WardRepository wardRepository;
    private final HomestayRepository homestayRepository;

    public DataSeeder (CityRepository cityRepository,
                       DistrictRepository districtRepository,
                       WardRepository wardRepository,
                       HomestayRepository homestayRepository){
        this.wardRepository = wardRepository;
        this.cityRepository = cityRepository;
        this.districtRepository = districtRepository;
        this.homestayRepository = homestayRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        homestayRepository.deleteAll();
        wardRepository.deleteAll();
        districtRepository.deleteAll();
        cityRepository.deleteAll();

        CityEntity daNang = CityEntity.builder().name("Da Nang").build();
        CityEntity haNoi = CityEntity.builder().name("Ha Noi").build();
        CityEntity hoChiMinh = CityEntity.builder().name("Ho Chi Minh").build();

        cityRepository.saveAll(List.of(daNang, haNoi, hoChiMinh));

        DistrictEntity haiChau = DistrictEntity.builder().name("Hai Chau").cityEntity(daNang).build();
        DistrictEntity thanhKhe = DistrictEntity.builder().name("Thanh Khe").cityEntity(daNang).build();

        DistrictEntity hoanKiem = DistrictEntity.builder().name("Hoan Kiem").cityEntity(haNoi).build();
        DistrictEntity dongDa = DistrictEntity.builder().name("Dong Da").cityEntity(haNoi).build();

        DistrictEntity quan1 = DistrictEntity.builder().name("Quan 1").cityEntity(hoChiMinh).build();
        DistrictEntity binhThanh = DistrictEntity.builder().name("Binh Thanh").cityEntity(hoChiMinh).build();

        districtRepository.saveAll(List.of(haiChau, thanhKhe, hoanKiem, dongDa, quan1, binhThanh));


        WardEntity thachThang = WardEntity.builder().name("Thach Thang").districtEntity(haiChau).build();
        WardEntity binhHien = WardEntity.builder().name("Binh Hien").districtEntity(haiChau).build();

        WardEntity hangTrong = WardEntity.builder().name("Hang Trong").districtEntity(hoanKiem).build();
        WardEntity cotCo = WardEntity.builder().name("Cot Co").districtEntity(dongDa).build();

        WardEntity benNghe = WardEntity.builder().name("Ben Nghe").districtEntity(quan1).build();
        WardEntity ward26 = WardEntity.builder().name("Ward 26").districtEntity(binhThanh).build();

        wardRepository.saveAll(List.of(thachThang, binhHien, hangTrong, cotCo, benNghe, ward26));

    }
}
