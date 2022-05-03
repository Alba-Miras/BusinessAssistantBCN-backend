package com.businessassistantbcn.opendata.security;

import com.businessassistantbcn.opendata.controller.OpendataController;
import com.businessassistantbcn.opendata.service.config.TestService;
import com.businessassistantbcn.opendata.service.externaldata.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;

@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureWebTestClient
@WebMvcTest(OpendataController.class)
public class OpendataAuthTest {

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestService testService;
    @MockBean
    private BigMallsService bigMallsService;
    @MockBean
    private MarketFairsService marketFairsService;
    @MockBean
    private MunicipalMarketsService municipalMarketsService;
    @MockBean
    private LargeEstablishmentsService largeEstablishmentsService;
    @MockBean
    private CommercialGalleriesService commercialGalleriesService;
    @MockBean
    private EconomicActivitiesCensusService economicActivitiesCensusService;

    @BeforeEach
    void setUp() {
        this.webTestClient = MockMvcWebTestClient
            .bindTo(mockMvc)
            .build();
    }

    @Test
    //@WithMockUser(username = "admin", password = "random", authorities= "ADMIN")
    void opendataAuthValidUserTest() {
        webTestClient.get()
            .uri("/businessassistantbcn/api/v1/opendata/test")
            .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqdmljZW50ZUBnbWFpbC5jb20iLCJhdXRob3JpdGllcyI6WyJBRE1JTiJdLCJpYXQiOjE2NDc1MzI1NjcsImV4cCI6MTY3OTA2ODU2N30.JT164AEVCUTxDfABefHWnsJowyy5C8BZZ1Z2OGWZTPQ")
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk()
            .expectBody(String.class)
                .isEqualTo("Hello from BusinessAssistant Barcelona!!!");
    }

}
