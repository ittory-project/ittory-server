package com.ittory.domain.member.service;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDomainService {

    private final MemberRepository memberDomainRepository;

    public Member saveMember(String email, String name) {
        return memberDomainRepository.save(Member.create(email, name, null));
    }

    public Member findMemberById(Long memberId) {

        return memberDomainRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
    }
}
