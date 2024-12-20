package com.ittory.domain.member.repository;

import com.ittory.domain.member.domain.MemberWithdraw;
import com.ittory.domain.member.repository.impl.MemberWithdrawRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberWithdrawRepository extends JpaRepository<MemberWithdraw, Long>, MemberWithdrawRepositoryCustom {
}
