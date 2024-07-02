package ru.gazprombank.lukinamiddleservice.user;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserControllerTest {
    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private UserController userController;
    @Test
    public void testRegisterUser_SuccessfulResponse() {
        MockitoAnnotations.initMocks(this);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.NO_CONTENT));

        String actual = userController.registerUser(jsonStringValid);
        assertEquals("OK", actual);
    }

    @Test
    public void testRegisterUser_ErrorResponse() {
        MockitoAnnotations.initMocks(this);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST));

        String actual = userController.registerUser(jsonStringValid);
        assertEquals("Произошло что-то ужасное, но станет лучше, честно", actual);
    }

    @Test
    public void testRegisterUser_InternalServerError() {
        MockitoAnnotations.initMocks(this);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));

        String actual = userController.registerUser(jsonStringValid);
        assertEquals("UNKNOWN ERROR", actual);
    }

    @Test
    public void testRegisterUser_InvalidRequest() {
        MockitoAnnotations.initMocks(this);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(HttpEntity.class), eq(String.class)))
                .thenReturn(new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR));

        String actual = userController.registerUser(jsonStringInValid);
        assertEquals("UNKNOWN ERROR", actual);
    }

    private String jsonStringValid = "{\"telegram_user_id\": \"111111111\", \"telegram_user_name\": \"joeblack\"}";
    private String jsonStringInValid = "{\"telegram_user_id\": \"111111111\"}";
    private String errorMessage =  "{\"message\": \"Произошло что-то ужасное, но станет лучше, честно\", \"type\": \"GeneralError\", \"code\": \"123\", \"traceId\": \"5f59e024-03c7-498d-9fc9-b8b15fd49c47\"}";
}
