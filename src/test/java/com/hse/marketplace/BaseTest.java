package com.hse.marketplace;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@DirtiesContext
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class BaseTest {

    protected final MockMvc mockMvc;

    BaseTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }
}
