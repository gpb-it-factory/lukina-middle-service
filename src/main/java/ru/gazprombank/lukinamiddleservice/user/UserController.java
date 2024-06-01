package ru.gazprombank.lukinamiddleservice.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @PostMapping
    public String registerUser() {
        return "OK";
    }
    @GetMapping("/test")
    public String test() {
        return "I'm alive!";
    }
}
