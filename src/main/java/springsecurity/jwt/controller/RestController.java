package springsecurity.jwt.controller;

import org.springframework.web.bind.annotation.GetMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @GetMapping("/")
    public String test() {
        return "JWT test worked";
    }
}
