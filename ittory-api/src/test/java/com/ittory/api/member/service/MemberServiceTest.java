package com.ittory.api.member.service;

import com.ittory.api.member.dto.MemberDetailResponse;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.LetterBoxDomainService;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.domain.member.service.MemberWithdrawDomainService;
import com.ittory.infra.discord.DiscordWebHookService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.when;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @MockBean
    private MemberDomainService memberDomainService;

    @MockBean
    private LetterBoxDomainService letterBoxDomainService;

    @MockBean
    private MemberWithdrawDomainService memberWithdrawDomainService;

    @MockBean
    private DiscordWebHookService discordWebHookService;


    @DisplayName("참여자 편지함에서 편지 삭제")
    @Test
    public void getMemberDetailsTest() {
        // given
        Long memberId = 1L;
        Member member = Member.builder()
                .id(memberId)
                .name("test")
                .build();
        when(memberDomainService.findMemberById(memberId)).thenReturn(member);

        // when
        MemberDetailResponse memberDetails = memberService.getMemberDetails(memberId);

        // then
        Assertions.assertThat(memberDetails.getMemberId()).isEqualTo(memberId);

    }

}
