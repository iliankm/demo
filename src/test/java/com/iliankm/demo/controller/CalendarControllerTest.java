package com.iliankm.demo.controller;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test for {@link CalendarController}.
 */
@WebMvcTest
@ContextConfiguration(classes = {CalendarController.class})
class CalendarControllerTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    void shouldGetCalendarEvent() {

        // given & when & then
        mvc.perform(get("/api/v1/calendar"))
                .andExpect(status().isOk())
                .andExpect(header().exists("Meeting-Id"));
    }
}