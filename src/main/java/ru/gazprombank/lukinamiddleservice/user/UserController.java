package ru.gazprombank.lukinamiddleservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    public String registerUser() {
        String url = this.host + "/users";
        String response = restTemplate.postForObject(url, null, String.class);
        System.out.println("Response from register: " + response);
        return response;
    }
    @GetMapping("/test")
    public String test() {
        return "I'm alive!";
    }
}
