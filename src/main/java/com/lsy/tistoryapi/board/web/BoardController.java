package com.lsy.tistoryapi.board.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("")
public class BoardController {

    @Value("${open-api.app-id}")
    private String appId;

    @Value("${open-api.secret-key}")
    private String secretKey;

    @Value("${open-api.access-token}")
    private String accessToken;
    
    @GetMapping("/")
    public String home() {
        log.info("accessToken = {}", accessToken);
        return "pages/index";
    }
}
