package com.kieranmangan.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kieranmangan.auth.model.UserForm;
import com.kieranmangan.auth.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthApplicationTests {

    private static final String LOGIN_REQUEST_BODY = "grant_type=password&client_id=myClient&username=%s&password=%s";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;

    @Test
    public void userShouldBeAbleToGetToken() throws Exception {
        // Given - a user
        mvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsBytes(UserForm.builder().id(UUID.randomUUID()).username("user1@testing.com").password("password").build()))
                        .header("content-type", "application/json"))
                .andExpect(status().isCreated());

        mvc.perform(
                post("/oauth/token")
                        .content(String.format(LOGIN_REQUEST_BODY, "user1@testing.com", "password"))
                        .header("content-type", "application/x-www-form-urlencoded"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.access_token").isString());
    }

    @Test
    public void invalidPasswordShouldReturn401() throws Exception {
        // Given - a user
        mvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsBytes(UserForm.builder().id(UUID.randomUUID()).username("user2@testing.com").password("password").build()))
                        .header("content-type", "application/json"))
                .andExpect(status().isCreated());

        mvc.perform(
                post("/oauth/token")
                        .content(String.format(LOGIN_REQUEST_BODY, "user2@testing.com", "wrong"))
                        .header("content-type", "application/x-www-form-urlencoded"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldBeAbleToPersistUser() throws Exception {
        UUID userId = UUID.randomUUID();
        mvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsBytes(UserForm.builder().id(userId).username("user3@testing.com").password("password").build()))
                        .header("content-type", "application/json"))
                .andExpect(status().isCreated());

        assertThat(userRepository.findById(userId)).isNotNull();
    }

    @Test
    public void shouldReturnBadRequestIfFieldsNotPresent() throws Exception {
        // No id
        mvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsBytes(UserForm.builder().username("user4@testing.com").password("password").build()))
                        .header("content-type", "application/json"))
                .andExpect(status().isBadRequest());
        // No username
        mvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsBytes(UserForm.builder().id(UUID.randomUUID()).password("password").build()))
                        .header("content-type", "application/json"))
                .andExpect(status().isBadRequest());
        // No password
        mvc.perform(
                post("/user")
                        .content(objectMapper.writeValueAsBytes(UserForm.builder().id(UUID.randomUUID()).username("user5@testing.com").build()))
                        .header("content-type", "application/json"))
                .andExpect(status().isBadRequest());
    }
}
