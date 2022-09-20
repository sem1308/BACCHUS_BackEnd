package uos.seclass.bacchus.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/")
    public String main() {
        return "welcome";
    }

    @GetMapping("/test")
    public Map userResponseTest() {
        Map<String, String> result = new HashMap<>();
        result.put("test", " k");
        return result;
    }
}