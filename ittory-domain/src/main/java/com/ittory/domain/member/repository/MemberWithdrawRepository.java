package com.ittory.domain.member.repository;

import com.ittory.domain.member.domain.MemberWithdraw;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberWithdrawRepository extends JpaRepository<MemberWithdraw, Long> {
}