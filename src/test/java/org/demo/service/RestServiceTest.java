package org.demo.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.demo.dto.PotatoBag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Date;

import static org.demo.service.RestService.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RestServiceTest {

    @Autowired
    private RestService restService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(restService).build();
        repositoryService.reset();
    }

    @Test
    public void healthCheckTest() throws Exception {
        mockMvc.perform(get("/")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().json("{" +
                            "\"bags\":[]," +
                            "\"status\":\"" + HEALTH_STATUS +
                        "\"}"));

    }

    @Test
    public void addTest() throws Exception {
        mockMvc.perform(post("/add").
                content(new ObjectMapper().writeValueAsString(PotatoBag.builder().potatoCount(50).price(10D).supplier("De Coster").packDate(new Date(1520546762249L)).build())).
                contentType(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().isCreated()).
                andExpect(content().string(containsString("\"potatoCount\":50,\"price\":10.0,\"supplier\":\"De Coster\",\"packDate\":\"2018-03-08 10:06:02 PM UTC\"}],\"status\":\"" + ADD_TO_MARKET + "\"}")));

    }

    @Test
    public void addBadTest() throws Exception {
        mockMvc.perform(post("/add").
                content(new ObjectMapper().writeValueAsString(PotatoBag.builder().potatoCount(250).price(-10D).supplier("XYZ").packDate(new Date(1520546762249L)).build())).
                contentType(MediaType.APPLICATION_JSON)).
                andDo(print()).
                andExpect(status().isBadRequest()).
                andExpect(content().json("{" +
                        "\"bags\":" +
                        "[" +
                            "{" +
                                "\"uuid\":null," +
                                "\"potatoCount\":250," +
                                "\"price\":-10.0," +
                                "\"supplier\":\"XYZ\"," +
                                "\"packDate\":\"2018-03-08 10:06:02 PM UTC\"" +
                            "}" +
                        "]," +
                        "\"status\":\"" + NOT_ADD_TO_MARKET + " - [Potato count is invalid, Price is invalid, Supplier is invalid]" +
                        "\"}"));
    }

    @Test
    public void getTest() throws Exception {

        mockMvc.perform(get("/get")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().json("{" +
                        "\"bags\":[]," +
                        "\"status\":\"" + FETCHED_FROM_MARKET +
                        "\"}"));
    }

    @Test
    public void getCountTest() throws Exception {

        mockMvc.perform(get("/get/5")).
                andDo(print()).
                andExpect(status().isOk()).
                andExpect(content().json("{" +
                        "\"bags\":[]," +
                        "\"status\":\"" + FETCHED_FROM_MARKET +
                        "\"}"));
    }
}
