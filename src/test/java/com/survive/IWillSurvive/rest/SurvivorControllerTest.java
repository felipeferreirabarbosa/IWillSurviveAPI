package com.survive.IWillSurvive.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.survive.IWillSurvive.model.entity.Status;
import com.survive.IWillSurvive.model.entity.Survivor;
import com.survive.IWillSurvive.model.repository.ItemsPointsRepository;
import com.survive.IWillSurvive.model.repository.StatusRepository;
import com.survive.IWillSurvive.model.repository.SurvivorRepository;
import com.survive.IWillSurvive.service.SurvivorService;
import com.survive.IWillSurvive.service.TradeService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(JUnitPlatform.class)
@WebMvcTest(SurvivorController.class)
public class SurvivorControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private SurvivorService survivorServiceMock;

    @MockBean
    private TradeService tradeServiceMock;


    @Test
    public void createTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/survivors/create")
                .content(asJsonString(Survivor.builder().name("Obj-1").age(25).gender("Male").inventory("Fiji Water:10,AK47:5,Campbell Soup:2").lastLocation("(0,0)")
                        .status(Status.builder().infected(false).build()).build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void createLocationFailParenthesisTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/survivors/create")
                .content(asJsonString(Survivor.builder().name("Obj-1").age(25).gender("Male").inventory("Fiji Water:10,AK47:5,Campbell Soup:2").lastLocation("0,0)")
                        .status(Status.builder().infected(false).build()).build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void createLocationFailNumberTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/api/survivors/create")
                .content(asJsonString(Survivor.builder().name("Obj-1").age(25).gender("Male").inventory("Fiji Water:10,AK47:5,Campbell Soup:2").lastLocation("(c,0)")
                        .status(Status.builder().infected(false).build()).build()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void attLocationTest() throws Exception {
        Map<String,String> build = new HashMap<String, String>();
        build.put("location","(4,2)");

        mvc.perform(MockMvcRequestBuilders.put("/api/survivors/{idSurvivor}/location",1)
                .content(asJsonString(build))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    @Test
    public void attLocationFailTest() throws Exception {

        mvc.perform(MockMvcRequestBuilders.put("/api/survivors/{idSurvivor}/location",1)
                .content("")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}