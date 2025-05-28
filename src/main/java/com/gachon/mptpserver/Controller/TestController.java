package com.gachon.mptpserver.Controller;

import com.gachon.mptpserver.DTO.Post;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/test")
    public String getPosts() {
        return "test ok";
    }
}
