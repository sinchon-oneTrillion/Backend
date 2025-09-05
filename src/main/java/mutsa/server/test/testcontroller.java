package mutsa.server.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class testcontroller {
    @GetMapping("/test")
    public String test() {
        return "CORS OK! 백엔드 연결 성공!";
    }
}
