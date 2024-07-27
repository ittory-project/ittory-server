package com.ittory.api.auth.usecase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.ittory.api.auth.dto.TokenRefreshResponse;
import com.ittory.api.auth.exception.AuthException.NoRefreshTokenException;
import com.ittory.api.auth.exception.AuthException.RefreshTokenNotMatchException;
import com.ittory.common.jwt.JwtProvider;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.MemberStatus;
import com.ittory.domain.member.service.MemberDomainService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenRefreshUseCaseTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private MemberDomainService memberDomainService;

    @InjectMocks
    private TokenRefreshUseCase tokenRefreshUseCase;

    @DisplayName("리프레시 토큰으로 엑세스 토큰을 갱신할 수 있다.")
    @Test
    void executeTest() {
        //given
        String accessToken = "expired.access.token";
        String refreshToken = "member.refresh.token";
        Member member = Member.builder()
                .id(1L)
                .socialId(1L)
                .name("test man")
                .profileImage(null)
                .memberStatus(MemberStatus.ACTIVE)
                .refreshToken(refreshToken)
                .build();

        when(jwtProvider.getSubByExpiredToken(any(String.class))).thenReturn(1L);
        when(memberDomainService.findMemberById(any(Long.class))).thenReturn(member);
        when(jwtProvider.createAccessToken(any(Long.class), any(String.class))).thenReturn("new.access.token");

        //when
        TokenRefreshResponse execute = tokenRefreshUseCase.execute(accessToken, refreshToken);

        //then
        assertThat(execute.getAccessToken()).isEqualTo("new.access.token");

    }

    @DisplayName("사용자 DB에 리프레시 토큰이 없으면 예외 발생.")
    @Test
    void executeTest_NoRefreshTokenException() {
        //given
        String accessToken = "expired.access.token";
        String refreshToken = "member.refresh.token";
        Member member = Member.builder()
                .id(1L)
                .socialId(1L)
                .name("test man")
                .profileImage(null)
                .memberStatus(MemberStatus.ACTIVE)
                .refreshToken(null)
                .build();

        when(jwtProvider.getSubByExpiredToken(any(String.class))).thenReturn(1L);
        when(memberDomainService.findMemberById(any(Long.class))).thenReturn(member);

        //when & then
        assertThatThrownBy(() -> tokenRefreshUseCase.execute(accessToken, refreshToken))
                .isInstanceOf(NoRefreshTokenException.class);

    }

    @DisplayName("리프레시 토큰이 일치하지 않으면 예외 발생.")
    @Test
    void executeTest_RefreshTokenNotMatchException() {
        //given
        String accessToken = "expired.access.token";
        String refreshToken = "wrong.refresh.token";
        Member member = Member.builder()
                .id(1L)
                .socialId(1L)
                .name("test man")
                .profileImage(null)
                .memberStatus(MemberStatus.ACTIVE)
                .refreshToken("member.refresh.token")
                .build();

        when(jwtProvider.getSubByExpiredToken(any(String.class))).thenReturn(1L);
        when(memberDomainService.findMemberById(any(Long.class))).thenReturn(member);

        //when & then
        assertThatThrownBy(() -> tokenRefreshUseCase.execute(accessToken, refreshToken))
                .isInstanceOf(RefreshTokenNotMatchException.class);

    }

}
