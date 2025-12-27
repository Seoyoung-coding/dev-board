package com.example.devboard.Controller;

import com.example.devboard.Service.BoardService;
import com.example.devboard.domain.BoardDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

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
        return "redirect:/board/list";  //저장이 끝나면 다시 목록 페이지로 돌아옴 (목록 갱신됨)
    }

    @GetMapping("/board/view")
    public String viewPage(@RequestParam("id") Long id, Model model) {
        // 1. 주소창의 ?id=번호 를 읽어서 DB에서 해당 글을 찾습니다.
        model.addAttribute("board", boardService.findBoardById(id));
        // 상세 내용을 담은줌view.html 화면을 보여줌
        return "view";
    }

    @PostMapping("/board/delete")
    public String deleteProcess(@RequestParam("id") Long id) {
        // 서비스에게 해당 ID의 글을 지우라고 시킴
        boardService.deleteBoard(id);
        return "redirect:/board/list";  //list로 옮겨줌
    }
    // 아래는 수정 로직 관련
    @GetMapping("board/edit")
    public String editPage(@RequestParam("id") Long id, Model model) {
        model.addAttribute("board", boardService.findBoardById(id));
        return "edit"; //edit.html 파일을 찾게 됨
    }
    // 2. 수정 완료 버튼 눌렀을 때 처리
    @PostMapping("/board/update")
    public String updateProcess(BoardDomain board) {
        // 서비스의 저장 기능을 그대로 씀 (JPA는 ID가 있으면 덮어쓰기함)
        boardService.registerBoard(board);
        // 수정한 글의 상세 페이지로 다시 보냅니다.
        return "redirect:/board/view?id=" + board.getId();
    }
}
