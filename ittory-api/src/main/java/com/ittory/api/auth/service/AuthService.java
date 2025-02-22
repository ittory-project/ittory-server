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

    public TokenRefreshResponse renewToken(String accessToken, String refreshToken) {
        Long memberId = jwtProvider.getSubByExpiredToken(accessToken);
        Member member = memberDomainService.findMemberById(memberId);

        if (member.getRefreshToken() == null) {
            throw new AuthException.NoRefreshTokenException();
        }

        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new AuthException.RefreshTokenNotMatchException(refreshToken);
        }

        String newCreatedAccessToken = createdAccessToken(memberId);

        return TokenRefreshResponse.of(newCreatedAccessToken);

    }

    private String createdAccessToken(Long memberId) {
        return jwtProvider.createAccessToken(memberId, MEMBER);
    }
}
