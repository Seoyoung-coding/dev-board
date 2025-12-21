package com.example.devboard.Repository;

import com.example.devboard.domain.BoardDomain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<BoardDomain, Long> {
    // JpaRepository를 상속받아야 findAll()이 List<BoardDomain>을 반환합니다.
}