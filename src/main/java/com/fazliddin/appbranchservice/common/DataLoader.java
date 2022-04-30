package com.fazliddin.appbranchservice.common;

import com.fazliddin.appbranchservice.exception.RestException;
import com.fazliddin.library.entity.District;
import com.fazliddin.library.entity.Region;
import com.fazliddin.library.repository.DistrictRepository;
import com.fazliddin.library.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;

    @Value("${spring.sql.init.mode}")
    private String initialMode;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {
            List<Region> regions = new ArrayList<>();
            regions.add(new Region("Toshkent"));
            regions.add(new Region("Toshkent viloyati"));
            regions.add(new Region("Andijon viloyati"));
            regions.add(new Region("Farg'ona viloyati"));
            regions.add(new Region("Namangan viloyati"));
            regions.add(new Region("Sirdaryo viloyati"));
            regions.add(new Region("Jizzax viloyati"));
            regions.add(new Region("Qashqadaryo viloyati"));
            regions.add(new Region("Surxondaryo viloyati"));
            regions.add(new Region("Samarqand viloyati"));
            regions.add(new Region("Navoiy viloyati"));
            regions.add(new Region("Buxoro viloyati"));
            regions.add(new Region("Xorazm viloyati"));
            regions.add(new Region("Qoraqalpog'iston Respublikasi"));

            regionRepository.saveAll(regions);

            String[] districts = new String[]{
                    "Andijon tumani",
                    "Asaka tumani",
                    "Bo'ston tumani",
                    "Buloqboshi tumani",
                    "Izboskan tumani",
                    "Jalaquduq tumani",
                    "Marhamat tumani",
                    "Oltinko'l tumani",      // Dalvarzin mahallasi Dalvarzin ko'xhasi 81-uy bemalol kelorasilar.
                    "Paxtaobod tumani",
                    "Shahrixon tumani",
                    "Ulug'nor tumani",
                    "Xo'jaobod tumani"
            };

            Region region = regionRepository.findByName("Andijon viloyati").orElseThrow(() -> RestException.notFound("REGION"));

            List<District> districtList = new ArrayList<>();
            for (String district : districts) {
                districtList.add(
                        new District(district, region)
                );
            }
            districtRepository.saveAll(districtList);
        }

        //todo Sql init mode sout qilinyapti
        System.err.printf("Sql init mode is %s \n", initialMode);
    }
}

