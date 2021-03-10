package ru.otus.tasks.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SecurityTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith403() throws Exception {
        mvc.perform(get("/genre").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is3xxRedirection());
    }

    @WithMockUser
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/genre").contentType(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk());
    }

    @WithMockUser
    @Test
    public void givenAuthRequestOnAdminService_shouldFailedWith403() throws Exception {
        mvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @WithMockUser(roles = "ADMIN")
    @Test
    public void givenAuthRequestOnAdminService_shouldSucceedWith200() throws Exception {
        mvc.perform(get("/user").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
}
