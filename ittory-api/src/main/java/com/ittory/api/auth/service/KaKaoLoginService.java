package com.ittory.api.auth.service;

import com.ittory.api.auth.dto.AuthTokenResponse;
import com.ittory.common.jwt.JwtProvider;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.infra.discord.DiscordWebHookService;
import com.ittory.infra.oauth.kakao.KaKaoPlatformClient;
import com.ittory.infra.oauth.kakao.dto.KaKaoTokenResponse;
import com.ittory.infra.oauth.kakao.dto.MemberInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.ittory.common.constant.MemberRole.MEMBER;

@Service
@RequiredArgsConstructor
public class KaKaoLoginService {

    private final KaKaoPlatformClient kaKaoPlatformClient;
    private final JwtProvider jwtProvider;

    private final MemberDomainService memberDomainService;
    private final DiscordWebHookService discordWebHookService;

    public AuthTokenResponse loginOrRegister(String authorizationCode, boolean isCode) {
        String accessToken = authorizationCode;
        if (isCode) {
            KaKaoTokenResponse tokenResponse = kaKaoPlatformClient.getKakaoAccessToken(authorizationCode);
            System.out.println("====== 11");
            accessToken = tokenResponse.getAccessToken();
            System.out.println("====== 22");
        }
        MemberInfo memberInfo = kaKaoPlatformClient.getMemberInfo(accessToken);
        Member member = memberDomainService.findMemberBySocialId(memberInfo.getSocialId());

        if (member == null) {
            member = memberDomainService.saveMember(
                    memberInfo.getSocialId(),
                    memberInfo.getName(),
                    memberInfo.getProfileUrl()
            );
            discordWebHookService.sendSingupMessage(member);
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
