package com.example.devboard.Controller;

import com.example.devboard.Service.BoardService;
import com.example.devboard.domain.BoardDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class BoardViewController {

    private final BoardService boardService;

    @GetMapping("/board/list")
    public String listPage(Model model) {
        // DB에서 글 목록을 가져와서 'boardList'라는 이름으로 HTML에 전달
        model.addAttribute("boardList", boardService.findAllBoards());
        return "list"; // templates/list.html을 찾아갑니다.
    }

    @GetMapping("/board/write")
    public String writePage() {
        return "write"; //templates 내의 write.html 파일을 열어줌\
    }

    @PostMapping("/board/write")
    public String writeProcess(BoardDomain board) {
        boardService.registerBoard(board);  // 화면에서 보낸 제목, 내용이 board 객체로 DB에 저장됨

        // 저장이 끝나면 다시 글 목록 화면 (/board/list)으로 돌아가게 됨
        return "redirect:/board/list";
    }
}
