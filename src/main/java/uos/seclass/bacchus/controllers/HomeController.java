package uos.seclass.bacchus.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//sudo nohup ./gradlew bootRun > nohup.log 2>&1 &
@RestController()
@RequestMapping("/")
public class HomeController {

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

