package com.example.devboard.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("board")

public class BoardDomain {
    @Id
    // 필드 작성
    private Long id;
    private String name;
    private String title;
    private String content;
    private String password;
    LocalDateTime createTime;
    LocalDateTime updatedAt;

    // 비지니스 메소드 작성
    boolean isPasswordCorrect (String inputPassword) {
        return this.password.equals(inputPassword);  // 암호 비교 로직
    }
    public void update (String name, String title, String content) {
        this.name = name;
        this.title = title;
        this.content = content;
        this.updatedAt = LocalDateTime.now();   //이 객체의 createdAt 필드에 지금시간을 넣는다
    }
}
