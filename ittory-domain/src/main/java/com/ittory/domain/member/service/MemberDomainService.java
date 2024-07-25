package com.ittory.domain.member.service;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberDomainService {

    private final MemberRepository memberDomainRepository;

    public Member saveMember(Long socialId, String name, String profileImage) {
        return memberDomainRepository.save(Member.create(socialId, name, profileImage));
    }

    public Member findMemberById(Long memberId) {
        return memberDomainRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
    }

    public Member findMemberBySocialId(Long socialId) {
        return memberDomainRepository.findBySocialId(socialId).orElse(null);
    }

    public void changeRefreshToken(Member member, String memberRefreshToken) {
        member.changeRefreshToken(memberRefreshToken);
        memberDomainRepository.save(member);
    }

}
