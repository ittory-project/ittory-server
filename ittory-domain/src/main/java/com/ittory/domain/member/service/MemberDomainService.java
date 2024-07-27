package com.ittory.domain.member.service;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.exception.MemberException.MemberNotFoundException;
import com.ittory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberDomainService {

    private final MemberRepository memberDomainRepository;

    @Transactional
    public Member saveMember(Long socialId, String name, String profileImage) {
        return memberDomainRepository.save(Member.create(socialId, name, profileImage));
    }

    @Transactional(readOnly = true)
    public Member findMemberById(Long memberId) {
        return memberDomainRepository.findById(memberId).orElseThrow(() -> new MemberNotFoundException(memberId));
    }

    @Transactional(readOnly = true)
    public Member findMemberBySocialId(Long socialId) {
        return memberDomainRepository.findBySocialId(socialId).orElse(null);
    }

    @Transactional
    public void changeRefreshToken(Member member, String memberRefreshToken) {
        member.changeRefreshToken(memberRefreshToken);
        memberDomainRepository.save(member);
    }

}
