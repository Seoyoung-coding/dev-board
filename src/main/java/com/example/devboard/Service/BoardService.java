package com.example.devboard.Service;

import com.example.devboard.Repository.BoardRepository;
import com.example.devboard.domain.BoardDomain;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class BoardService {
    // db와 대화함
    private final BoardRepository boardRepository;
    // 글 등록 기능
    public void registerBoard(BoardDomain board) {
        board.setCreateTime(LocalDateTime.now());   // 등록일 생성
        boardRepository.save(board);    //ID 자동 생성
    }
    // 글 전체 조회 기능
    public List<BoardDomain> findAllBoards() {
        return boardRepository.findAll();
    }
}
