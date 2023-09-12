package com.lsy.tistoryapi.board.web;

import com.lsy.tistoryapi.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardRestController {

    private final BoardService boardService;

    @GetMapping("")
    public ResponseEntity getPosts() {
        return boardService.getPosts();
    }
}
