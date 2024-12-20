package com.ittory.domain.member.repository.impl;

import java.time.LocalDate;

public interface MemberWithdrawRepositoryCustom {
    long countWithdrawByDate(LocalDate date);
}
