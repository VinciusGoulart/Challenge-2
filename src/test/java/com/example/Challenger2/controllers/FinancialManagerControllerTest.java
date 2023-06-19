package com.example.Challenger2.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
@SpringBootTest
class FinancialManagerControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Must return 200 if it has a summary for this year-month")
    void monthSummary() throws Exception {
        Integer year = 1999;
        Integer month = 10;

        var response = mvc.perform(get("/results/{year}/{month}", year, month)
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("\"monthBalance\":2.50");
    }

    @Test
    @DisplayName("Must return 200 if there is no summary for this year-month")
    void monthSummary2() throws Exception {
        Integer year = 1999;
        Integer month = 03;

        var response = mvc.perform(get("/results/{year}/{month}", year, month)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).doesNotContain("monthBalance");
    }
}