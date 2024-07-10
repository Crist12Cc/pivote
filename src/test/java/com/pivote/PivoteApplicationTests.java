package com.pivote;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.serfinsa.frameworks.models.cardinfo.entities.Country;
import org.serfinsa.frameworks.models.cardinfo.repositories.ICountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Log4j2
@SpringBootTest
class PivoteApplicationTests {

	@Autowired
	ICountryRepository iCountryRepository;

	@Autowired
	ObjectMapper objectMapper;

	@Test
	void contextLoads() {
		List<Country> countries = iCountryRepository.findAll();
        try {
            log.info("info {}", objectMapper.writeValueAsString(countries));
        } catch (JsonProcessingException e) {
            log.error(e);
        }
    }

}
