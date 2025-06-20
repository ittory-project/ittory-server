package com.ittory.api.auth.service;

import com.ittory.api.auth.dto.TokenRefreshResponse;
import com.ittory.api.auth.exception.AuthException;
import com.ittory.common.jwt.JwtProvider;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.MemberStatus;
import com.ittory.domain.member.service.MemberDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthServiceTest {

    @InjectMocks
    private AuthService authService;

    @Mock
    private MemberDomainService memberDomainService;

    @Mock
    private JwtProvider jwtProvider;

    @Test
    void logOutTest() {
        //given
        Member member = Member.builder()
                .id(1L)
                .socialId(1L)
                .name("test man")
                .profileImage(null)
                .memberStatus(MemberStatus.ACTIVE)
                .refreshToken("old.refresh.token")
                .build();

        when(memberDomainService.findMemberById(member.getId())).thenReturn(member);

        //when
        authService.logout(member.getId());

        //then
        verify(memberDomainService).changeRefreshToken(member, null);
    }

    @DisplayName("리프레시 토큰으로 엑세스 토큰을 갱신할 수 있다.")
    @Test
    void renewTokenTest() {
        //given
        String refreshToken = "member.refresh.token";
        Member member = Member.builder()
                .id(1L)
                .socialId(1L)
                .name("test man")
                .profileImage(null)
                .memberStatus(MemberStatus.ACTIVE)
                .refreshToken(refreshToken)
                .build();

        when(jwtProvider.isRefreshToken(eq(refreshToken))).thenReturn(true);
        when(memberDomainService.findMemberByRefreshToken(any(String.class))).thenReturn(member);
        when(jwtProvider.createAccessToken(any(Long.class), any(String.class))).thenReturn("new.access.token");

        //when
        TokenRefreshResponse execute = authService.renewToken(refreshToken);

        //then
        assertThat(execute.getAccessToken()).isEqualTo("new.access.token");

    }

    @DisplayName("사용자 DB에 리프레시 토큰이 없으면 예외 발생.")
    @Test
    void renewTokenTest_NoRefreshTokenException() {
        //given
        String refreshToken = "member.refresh.token";
        Member member = Member.builder()
                .id(1L)
                .socialId(1L)
                .name("test man")
                .profileImage(null)
                .memberStatus(MemberStatus.ACTIVE)
                .refreshToken(null)
                .build();

        when(jwtProvider.isRefreshToken(eq(refreshToken))).thenReturn(true);
        when(memberDomainService.findMemberByRefreshToken(any(String.class))).thenReturn(null);

        //when & then
        assertThatThrownBy(() -> authService.renewToken(refreshToken))
                .isInstanceOf(AuthException.NoRefreshTokenException.class);

    }

    @DisplayName("토큰 타입이 REFRESH가 아닐 경우 예외 발생.")
    @Test
    void renewTokenTest_NotARefreshTokenTypeException() {
        //given
        String refreshToken = "member.refresh.token";
        Member member = Member.builder()
                .id(1L)
                .socialId(1L)
                .name("test man")
                .profileImage(null)
                .memberStatus(MemberStatus.ACTIVE)
                .refreshToken(null)
                .build();

        //when & then
        assertThatThrownBy(() -> authService.renewToken(refreshToken))
                .isInstanceOf(AuthException.NotARefreshTokenTypeException.class);

    }

}
