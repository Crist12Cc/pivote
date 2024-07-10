package com.pivote.controllers;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import lombok.RequiredArgsConstructor;
import org.serfinsa.frameworks.models.cardinfo.entities.Country;
import org.serfinsa.frameworks.models.cardinfo.repositories.ICountryRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/pivote")
@RequiredArgsConstructor
public class PivoteController {

    private final ICountryRepository countryRepository;

    @PostMapping
    public ResponseEntity<?> pivote(){
        return ResponseEntity.ok().build();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadCSVFile(@RequestParam("file") MultipartFile file) {
        try {
            Reader reader = new InputStreamReader(file.getInputStream());
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build(); // Skip header

            List<Country> entities = new ArrayList<>();
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                Country entity = new Country();
                // Map CSV columns to entity fields
                entity.setCountryName(nextRecord[1]);
                entity.setAlpha2Code(nextRecord[3]);
                entity.setAlpha3Code(nextRecord[2]);
                entity.setIsoCode(nextRecord[4]);
                entity.setRegionCode(nextRecord[13]);
                // Set other fields accordingly

                entities.add(entity);
            }

            // Save entities to database (example using JPA repository)
            countryRepository.saveAll(entities);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
