package com.survive.IWillSurvive.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survive.IWillSurvive.dto.InfectionReportDTO;
import com.survive.IWillSurvive.service.InfectionReportService;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(JUnitPlatform.class)
@WebMvcTest(InfectionReportController.class)
public class InfectionReportControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private InfectionReportService infectionReportServiceMock;

    @Test
    public void reportInfectedTest() throws Exception {
        InfectionReportDTO infectionReportDTO = new InfectionReportDTO();
        infectionReportDTO.setIdInfected(1);
        infectionReportDTO.setIdSurvivor(2);

        mvc.perform(MockMvcRequestBuilders.put("/api/infectionReport/reportInfected")
                .content(asJsonString(infectionReportDTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
