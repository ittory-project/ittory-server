package com.ittory.api.auth.usecase;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.enums.MemberStatus;
import com.ittory.domain.member.service.MemberDomainService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LogoutUseCaseTest {

    @Mock
    private MemberDomainService memberDomainService;

    @InjectMocks
    private LogoutUseCase logoutUseCase;

    @Test
    void executeTest() {
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
        logoutUseCase.execute(member.getId());

        //then
        verify(memberDomainService).changeRefreshToken(member, null);
    }

}
