package com.example.Challenger2.controllers;

import com.example.Challenger2.entities.DTOs.expenseDTOs.ExpenseDTO;
import com.example.Challenger2.entities.Expense;
import com.example.Challenger2.entities.enums.Category;
import com.example.Challenger2.services.ExpenseService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@SpringBootTest
class ExpenseControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ExpenseService expenseService;

    private JacksonTester<ExpenseDTO> dtoJacksonTester;

    @BeforeEach
    void setUp() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        JacksonTester.initFields(this, objectMapper);
    }

    @Test
    @Transactional
    @DisplayName("Must return code 200 if doesn't exist a expense with the same description and year/month")
    void saveTest1() throws Exception {
        ExpenseDTO dto = new ExpenseDTO(new Expense(null, "test2", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10), Category.Food));

        var response = mvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 400 if exist a expense with the same description and year/month")
    void saveTest2() throws Exception {
        ExpenseDTO dto = new ExpenseDTO(new Expense(null, "test", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10), Category.Food));

        var response = mvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 400 if mandatory fields price, date or category are not valid")
    void saveTest3() throws Exception {
        ExpenseDTO dto = new ExpenseDTO(new Expense(null, "test3", null,
                LocalDate.of(1999, 10, 10), Category.Food));

        var response = mvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto).getJson())
        ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 400 if business rules are not valid")
    void saveTest4() throws Exception {
        ExpenseDTO dto = new ExpenseDTO(new Expense(null, "", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10), Category.Food));
        ExpenseDTO dto2 = new ExpenseDTO(new Expense(null, "test4", new BigDecimal(-2500),
                LocalDate.of(1999, 10, 10), Category.Food));
        ExpenseDTO dto3 = new ExpenseDTO(new Expense(null, "test4", new BigDecimal(2500),
                LocalDate.of(2100, 10, 10), Category.Food));
        ExpenseDTO dto4 = new ExpenseDTO(new Expense(null, "test4", new BigDecimal(2500),
                LocalDate.of(1999, 10, 10), null));

        var response = mvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto).getJson())
        ).andReturn().getResponse();
        var response2 = mvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto2).getJson())
        ).andReturn().getResponse();
        var response3 = mvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto3).getJson())
        ).andReturn().getResponse();
        var response4 = mvc.perform(post("/expenses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(dtoJacksonTester.write(dto4).getJson())
        ).andReturn().getResponse();

        // Verify that the system correctly validates the blank description and returns the proper response code.
        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        // Verify that the system correctly validates the negative value and returns the proper response code.
        assertThat(response2.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        // Verify that the system correctly validates the future date and returns the proper response code.
        assertThat(response3.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());

        // Verifies that the system correctly validates the invalid category and returns the appropriate response code.
        assertThat(response4.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    @DisplayName("Must return code 200 if expense exists")
    void findById1() throws Exception {
        Long id = 1L;

        var response = mvc.perform(get("/expenses/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    @DisplayName("Must return code 404 if expense does not exists")
    void findById2() throws Exception {
        Long id = 999999999L;

        var response = mvc.perform(get("/expenses/{id}", id)).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    @Transactional
    @DisplayName("Must return code 200 and all expenses with same description")
    void findAllDescriptionCase() throws Exception {
        String description = "test";

        var response = mvc.perform(get("/expenses")
                        .param("search", description)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("test");
    }

    @Test
    @Transactional
    @DisplayName("Must return code 404 if description does not exists")
    void findAllDescriptionCase2() throws Exception {
        String description = "banana";

        var response = mvc.perform(get("/expenses")
                        .param("search", description)
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();


        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }
}