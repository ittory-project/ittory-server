package com.ittory.domain.member.repository;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.MemberStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findBySocialId(Long socialId);

    long countByMemberStatus(MemberStatus memberStatus);
}
