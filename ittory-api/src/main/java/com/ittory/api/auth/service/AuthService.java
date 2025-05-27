package com.ittory.api.auth.service;

import com.ittory.api.auth.dto.TokenRefreshResponse;
import com.ittory.api.auth.exception.AuthException;
import com.ittory.common.jwt.JwtProvider;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ittory.common.constant.MemberRole.MEMBER;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberDomainService memberDomainService;
    private final JwtProvider jwtProvider;


    public void logout(Long memberId) {
        Member member = memberDomainService.findMemberById(memberId);
        memberDomainService.changeRefreshToken(member, null);
    }

    public TokenRefreshResponse renewToken(String refreshToken) {

        if (!jwtProvider.isRefreshToken(refreshToken)) {
            throw new AuthException.NotARefreshTokenTypeException(refreshToken);
        }

        Member member = memberDomainService.findMemberByRefreshToken(refreshToken);

        if (member == null) {
            throw new AuthException.NoRefreshTokenException();
        }

        String newCreatedAccessToken = createdAccessToken(member.getId());

        return TokenRefreshResponse.of(newCreatedAccessToken);

    }

    private String createdAccessToken(Long memberId) {
        return jwtProvider.createAccessToken(memberId, MEMBER);
    }
}
