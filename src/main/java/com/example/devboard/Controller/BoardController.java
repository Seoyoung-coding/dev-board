package com.example.devboard.Controller;

import com.example.devboard.Service.BoardService;
import com.example.devboard.domain.BoardDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor

public class BoardController {
    private final BoardService boardService;

    // 글 등록 (이름, 제목, 암호, 본문 입력 받기)
    @PostMapping
    public String register(@RequestBody BoardDomain board) {
        boardService.registerBoard(board);
        return "글 등록이 완료되었습니다!";
    }

    @GetMapping
    public List<BoardDomain> list() {
        return boardService.findAllBoards();
    }
}
