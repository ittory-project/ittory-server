package com.ittory.domain.member.repository.impl;

import java.time.LocalDate;

public interface MemberRepositoryCustom {
    long countSignUpByDate(LocalDate date);
}
