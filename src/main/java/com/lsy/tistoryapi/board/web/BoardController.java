package com.lsy.tistoryapi.board.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequestMapping("")
public class BoardController {
    
    @GetMapping("/")
    public String home() {
        return "pages/index";
    }
}
