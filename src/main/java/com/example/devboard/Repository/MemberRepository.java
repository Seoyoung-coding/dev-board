package com.example.devboard.Repository;

import com.example.devboard.domain.MemberDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberDomain, Long> {
    Optional<MemberDomain> findByLoginId(String loginId);
}
