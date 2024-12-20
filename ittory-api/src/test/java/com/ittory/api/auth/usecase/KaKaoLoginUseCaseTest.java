package com.ittory.api.auth.usecase;

import com.ittory.api.auth.dto.AuthTokenResponse;
import com.ittory.common.jwt.JwtProvider;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.MemberStatus;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.infra.discord.DiscordWebHookService;
import com.ittory.infra.oauth.kakao.KaKaoPlatformClient;
import com.ittory.infra.oauth.kakao.dto.KaKaoTokenResponse;
import com.ittory.infra.oauth.kakao.dto.MemberInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class KaKaoLoginUseCaseTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private MemberDomainService memberDomainService;
    @Mock
    private KaKaoPlatformClient kaKaoPlatformClient;

    @Mock
    private DiscordWebHookService discordWebHookService;

    @InjectMocks
    private KaKaoLoginUseCase kaKaoLoginUseCase;

    //Member.create(1L, "test man", null)
    @Test
    void executeTest_Save() {
        //given
        String kakaoCode = "kakao_code";
        String kakaoAccessToken = "kakao_access_token";
        Member member = Member.builder()
                .id(1L)
                .socialId(1L)
                .name("test man")
                .profileImage(null)
                .memberStatus(MemberStatus.ACTIVE)
                .refreshToken(null)
                .build();

        when(kaKaoPlatformClient.getToken(any(String.class))).thenReturn(new KaKaoTokenResponse(kakaoAccessToken));
        when(kaKaoPlatformClient.getMemberInfo(any(String.class))).thenReturn(new MemberInfo(1L, "test man", null));
        when(memberDomainService.findMemberBySocialId(any(Long.class))).thenReturn(null);
        when(memberDomainService.saveMember(1L, "test man", null)).thenReturn(member);
        when(jwtProvider.createAccessToken(any(Long.class), any(String.class))).thenReturn("access.token");
        when(jwtProvider.createRefreshToken(any(Long.class))).thenReturn("refresh.token");
        doNothing().when(discordWebHookService).sendSingupMessage(any(Member.class));

        //when
        AuthTokenResponse execute = kaKaoLoginUseCase.execute(kakaoCode);

        Assertions.assertThat(execute.getAccessToken()).isEqualTo("access.token");
        Assertions.assertThat(execute.getRefreshToken()).isEqualTo("refresh.token");
    }

    @Test
    void executeTest_Find() {
        //given
        String kakaoCode = "kakao_code";
        String kakaoAccessToken = "kakao_access_token";
        Member member = Member.builder()
                .id(1L)
                .socialId(1L)
                .name("test man")
                .profileImage(null)
                .memberStatus(MemberStatus.ACTIVE)
                .refreshToken(null)
                .build();

        when(kaKaoPlatformClient.getToken(any(String.class))).thenReturn(new KaKaoTokenResponse(kakaoAccessToken));
        when(kaKaoPlatformClient.getMemberInfo(any(String.class))).thenReturn(new MemberInfo(1L, "test man", null));
        when(memberDomainService.findMemberBySocialId(any(Long.class))).thenReturn(member);
        when(jwtProvider.createAccessToken(any(Long.class), any(String.class))).thenReturn("access.token");
        when(jwtProvider.createRefreshToken(any(Long.class))).thenReturn("refresh.token");

        //when
        AuthTokenResponse execute = kaKaoLoginUseCase.execute(kakaoCode);

        Assertions.assertThat(execute.getAccessToken()).isEqualTo("access.token");
        Assertions.assertThat(execute.getRefreshToken()).isEqualTo("refresh.token");
    }

}
