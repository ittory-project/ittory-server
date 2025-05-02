package com.ittory.api.auth.service;

import com.ittory.api.auth.dto.AuthTokenResponse;
import com.ittory.common.jwt.JwtProvider;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.ittory.common.constant.MemberRole.MEMBER;

@Service
@RequiredArgsConstructor
public class IdLoginService {

    private final JwtProvider jwtProvider;
    private final MemberDomainService memberDomainService;

    @Transactional
    public AuthTokenResponse login(String loginId) {
        Member member = memberDomainService.findMemberByLoginId(loginId);
        return generateMemberToken(member);
    }

    private AuthTokenResponse generateMemberToken(Member member) {
        String memberAccessToken = jwtProvider.createAccessToken(member.getId(), MEMBER);
        String memberRefreshToken = jwtProvider.createRefreshToken(member.getId());
        memberDomainService.changeRefreshToken(member, memberRefreshToken);
        return AuthTokenResponse.of(memberAccessToken, memberRefreshToken);
    }

    @Transactional(readOnly = true)
    public List<String> findAllIdAuth() {
        return memberDomainService.findAllIdMember().stream()
                .map(Member::getLoginId)
                .toList();
    }
}
