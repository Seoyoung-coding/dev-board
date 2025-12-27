package com.example.devboard.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter

public class MemberDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)  //id는 중복되지 않도록 설정하기
    private String loginId;
    private String password;
    private String name;
    private LocalDateTime joinDate;
}