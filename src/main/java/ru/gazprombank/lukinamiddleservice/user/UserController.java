package ru.gazprombank.lukinamiddleservice.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
public class UserController {
    private String host;
    private RestTemplate restTemplate;

    @Autowired
    public UserController(@Value("${backend.host}") String host, RestTemplate restTemplate) {
        super();
        this.host = host;
        this.restTemplate = restTemplate;
    }
    @PostMapping
    public String registerUser(@RequestBody String request_body) {
        try {
            RegisterUserRequestBody registerUserRequestBody = objectMapper.readValue(request_body, RegisterUserRequestBody.class);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userId", registerUserRequestBody.telegram_user_id);
            jsonObject.put("userName", registerUserRequestBody.telegram_user_name);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> request = new HttpEntity(jsonObject.toString(), headers);

            String url = this.host + "/v2/users";
            ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

            HttpStatus status = responseEntity.getStatusCode();
            if (status == HttpStatus.NO_CONTENT) {
                return "OK";
            }
            return this.getErrorRegisterErrorMessage(responseEntity.getBody());

        }  catch (HttpServerErrorException e) {
            return this.getErrorRegisterErrorMessage(e.getResponseBodyAsString());
        } catch (Exception e) {
            return "UNKNOWN ERROR";
        }
    }

    private String getErrorRegisterErrorMessage(String responseBody) {
        try {
            RegisterUserResponseBody registerUserResponseBody = objectMapper.readValue(responseBody, RegisterUserResponseBody.class);
            return registerUserResponseBody.message;
        } catch (Exception e) {
            return "UNKNOWN ERROR";
        }
    }
    @GetMapping("/test")
    public String test() {
        return "I'm alive!";
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    public record RegisterUserRequestBody(Long telegram_user_id, String telegram_user_name) {}
    public record RegisterUserResponseBody(String message, String type, Integer code, String traceId) {}
}
