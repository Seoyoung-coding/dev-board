package com.example.devboard.Controller;

import ch.qos.logback.core.model.Model;
import com.example.devboard.Repository.MemberRepository;
import com.example.devboard.domain.MemberDomain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    // 1. 회원가입 화면을 보여주는 메서드 (브라우저 주소창에 입력했을 때)
    @GetMapping("/member/signup")
    public String signupPage() {
        return "signup"; // signup.html 파일을 보여줌
    }

    // 2. 회원가입 버튼을 눌렀을 때 데이터를 저장하는 메서드 (Form Submit 전용)
    @PostMapping("/member/signup")
    public String signupProcess(MemberDomain member) {
        member.setJoinDate(LocalDateTime.now());
        memberRepository.save(member);
        return "redirect:/board/list";
    }
}