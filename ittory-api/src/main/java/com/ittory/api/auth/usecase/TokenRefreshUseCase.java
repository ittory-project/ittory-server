package com.ittory.api.auth.usecase;

import static com.ittory.common.constant.MemberRole.MEMBER;

import com.ittory.api.auth.dto.TokenRefreshResponse;
import com.ittory.common.jwt.JwtProvider;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenRefreshUseCase {

    private final JwtProvider jwtProvider;
    private final MemberDomainService memberDomainService;

    public TokenRefreshResponse execute(String accessToken, String refreshToken) {
        Long memberId = jwtProvider.getSubByExpiredToken(accessToken);
        Member member = memberDomainService.findMemberById(memberId);

        if (member.getRefreshToken() == null) {
            throw new IllegalArgumentException();
        }

        if (!member.getRefreshToken().equals(refreshToken)) {
            throw new IllegalArgumentException();
        }

        String newCreatedAccessToken = newCreatedAccessToken(memberId);

        return TokenRefreshResponse.of(newCreatedAccessToken);

    }

    private String newCreatedAccessToken(Long memberId) {
        return jwtProvider.createAccessToken(memberId, MEMBER);
    }
}
