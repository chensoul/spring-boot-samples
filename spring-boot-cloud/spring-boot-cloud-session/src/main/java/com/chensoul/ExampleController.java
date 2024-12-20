package com.chensoul;

import jakarta.servlet.http.HttpSession;
import java.util.UUID;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @RequestMapping("/")
    public String hello(HttpSession session) {
        UUID uid = (UUID) session.getAttribute("uid");
        if (uid==null) {
            uid = UUID.randomUUID();
        }
        session.setAttribute("uid", uid);
        return uid.toString();
    }

}
