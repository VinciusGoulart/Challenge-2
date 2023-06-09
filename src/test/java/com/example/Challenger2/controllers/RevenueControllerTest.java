package com.example.Challenger2.controllers;

import com.example.Challenger2.entities.DTOs.revenueDTOs.RevenueDTO;
import com.example.Challenger2.entities.Revenue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@SpringBootTest
class RevenueControllerTest {

    @Autowired
    private MockMvc mvc;
    private JacksonTester<RevenueDTO> dtoJacksonTester;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    @Transactional
    @DisplayName("Must return code 200 if doesn't exist a revenue with the same description and year/month")
    void saveTest1() throws Exception {
        RevenueDTO dto = new RevenueDTO(new Revenue(null, "test2", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10)));

        var response = mvc.perform(post("/revenues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 400 if exist a revenue with the same description and year/month")
    void saveTest2() throws Exception {
        RevenueDTO dto = new RevenueDTO(new Revenue(null, "test", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10)));

        var response = mvc.perform(post("/revenues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 400 if mandatory fields price or  date  are not valid")
    void saveTest3() throws Exception {
        RevenueDTO dto = new RevenueDTO(new Revenue(null, "test3", null,
                LocalDate.of(1999, 10, 10)));

        var response = mvc.perform(post("/revenues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 400 if business rules are not valid")
    void saveTest4() throws Exception {
        RevenueDTO dto = new RevenueDTO(new Revenue(null, "", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10)));
        RevenueDTO dto2 = new RevenueDTO(new Revenue(null, "test4", new BigDecimal(-2500),
                LocalDate.of(1999, 10, 10)));
        RevenueDTO dto3 = new RevenueDTO(new Revenue(null, "test4", new BigDecimal(2500),
                LocalDate.of(2100, 10, 10)));


        var response = mvc.perform(post("/revenues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto).getJson())
        ).andReturn().getResponse();
        var response2 = mvc.perform(post("/revenues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto2).getJson())
        ).andReturn().getResponse();
        var response3 = mvc.perform(post("/revenues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto3).getJson())
        ).andReturn().getResponse();


        // Verify that the system correctly validates the blank description and returns the proper response code.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        // Verify that the system correctly validates the negative value and returns the proper response code.
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        // Verify that the system correctly validates the future date and returns the proper response code.
        assertThat(response3.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Must return code 200 if revenue exists")
    void findById1() throws Exception {
        Long id = 1L;

        var response = mvc.perform(get("/revenues/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Must return code 404 if revenue does not exists")
    void findById2() throws Exception {
        Long id = 999999999L;

        var response = mvc.perform(get("/revenues/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 200 and all revenues with same description")
    void findAllDescriptionCase() throws Exception {
        String description = "test";

        var response = mvc.perform(get("/revenues")
                        .param("search", description)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).doesNotContain("banana");
    }

    @Test
    @Transactional
    @DisplayName("Must return code 404 if description does not exists")
    void findAllDescriptionCase2() throws Exception {
        String description = "banana";

        var response = mvc.perform(get("/revenues")
                        .param("search", description)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).doesNotContain("banana");
    }

    @Test
    @Transactional
    @DisplayName("Must return code 200 and update the revenue")
    void update1() throws Exception {
        Long id = 1L;

        RevenueDTO dto = new RevenueDTO(new Revenue(null, "test2", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10)));

        var response = mvc.perform(put("/revenues/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write(dto).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 404 if revenue does not exists")
    void update2() throws Exception {
        Long id = 999L;

        RevenueDTO dto = new RevenueDTO(new Revenue(null, "test2", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10)));

        var response = mvc.perform(put("/revenues/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write(dto).getJson()))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 400 if business rules are not valid")
    void update3() throws Exception {
        Long id = 1L;

        RevenueDTO dto = new RevenueDTO(new Revenue(null, "", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10)));
        RevenueDTO dto2 = new RevenueDTO(new Revenue(null, "test4", new BigDecimal(-2500),
                LocalDate.of(1999, 10, 10)));
        RevenueDTO dto3 = new RevenueDTO(new Revenue(null, "test4", new BigDecimal(2500),
                LocalDate.of(2100, 10, 10)));


        var response = mvc.perform(put("/revenues/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write(dto).getJson()))
                .andReturn().getResponse();
        var response2 = mvc.perform(put("/revenues/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write(dto2).getJson()))
                .andReturn().getResponse();
        var response3 = mvc.perform(put("/revenues/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dtoJacksonTester.write(dto3).getJson()))
                .andReturn().getResponse();


        // Verify that the system correctly validates the blank description and returns the proper response code.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        // Verify that the system correctly validates the negative value and returns the proper response code.
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        // Verify that the system correctly validates the future date and returns the proper response code.
        assertThat(response3.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

    }

    @Test
    @Transactional
    @DisplayName("Must return 204 if revenue is deleted")
    void delete1() throws Exception {
        Long id = 1L;

        var response = mvc.perform(delete("/revenues/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return 404 if revenue does not exists")
    void delete2() throws Exception {
        Long id = 9999999L;

        var response = mvc.perform(delete("/revenues/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Transactional
    @DisplayName("You must return code 200 and all revenue with the date provided")
    void findByYearAndMonth() throws Exception {
        Integer year = 1999;
        Integer month = 10;

        var response = mvc.perform(get("/revenues/{year}/{month}", year, month)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).doesNotContain("1800-02");
    }

    @Test
    @Transactional
    @DisplayName("Must return code 404 if the revenue does not exist on the indicated date")
    void findByYearAndMonth2() throws Exception {
        Integer year = 2023;
        Integer month = 5;

        var response = mvc.perform(get("/revenues/{year}/{month}", year, month)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).doesNotContain("2023-05");
    }
}