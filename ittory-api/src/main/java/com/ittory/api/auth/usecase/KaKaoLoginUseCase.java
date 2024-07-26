package com.ittory.api.auth.usecase;

import static com.ittory.common.constant.MemberRole.MEMBER;

import com.ittory.api.auth.dto.AuthTokenResponse;
import com.ittory.common.jwt.JwtProvider;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.infra.oauth.kakao.KaKaoPlatformClient;
import com.ittory.infra.oauth.kakao.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KaKaoLoginUseCase {

    private final KaKaoPlatformClient kaKaoPlatformClient;
    private final JwtProvider jwtProvider;

    private final MemberDomainService memberDomainService;

    public AuthTokenResponse execute(String code) {
//        KaKaoTokenResponse kaKaoToken = kaKaoPlatformClient.getToken(code);
//        System.out.println(kaKaoToken.getAccessToken());
//        MemberInfo memberInfo = kaKaoPlatformClient.getMemberInfo(kaKaoToken.getAccessToken());
        MemberInfo memberInfo = kaKaoPlatformClient.getMemberInfo(code);
        Member member = memberDomainService.findMemberBySocialId(memberInfo.getSocialId());

        if (member == null) {
            member = memberDomainService.saveMember(
                    memberInfo.getSocialId(),
                    memberInfo.getName(),
                    memberInfo.getProfileUrl()
            );
        }

        return generateMemberToken(member);
    }

    private AuthTokenResponse generateMemberToken(Member member) {
        String memberAccessToken = jwtProvider.createAccessToken(member.getId(), MEMBER);
        String memberRefreshToken = jwtProvider.createRefreshToken(member.getId());
        memberDomainService.changeRefreshToken(member, memberRefreshToken);
        return AuthTokenResponse.of(memberAccessToken, memberRefreshToken);
    }
}
