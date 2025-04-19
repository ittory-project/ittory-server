package com.ittory.domain.member.repository.impl;

import com.ittory.domain.member.domain.Member;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberRepositoryCustom {
    long countSignUpByDate(LocalDate date);

    Optional<Member> findByLoginId(String loginId);
}
