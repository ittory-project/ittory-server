package com.ittory.api.member.service;

import com.ittory.api.member.dto.MemberDetailResponse;
import com.ittory.api.member.dto.ParticipationResponse;
import com.ittory.api.member.dto.ReceivedLetterResponse;
import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.dto.CoverTypeImages;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.service.LetterBoxDomainService;
import com.ittory.domain.member.service.MemberDomainService;
import com.ittory.domain.member.service.MemberWithdrawDomainService;
import com.ittory.infra.discord.DiscordWebHookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
        assertThat(memberDetails.getMemberId()).isEqualTo(memberId);
    }

    @DisplayName("참여자가 참여한 편지 조회")
    @Test
    public void getMemberParticipatedLettersTest() {
        // given
        Long memberId = 1L;
        CoverTypeImages images = new CoverTypeImages("url1", "url2", "url3", "url4", "url5", "url6", "url7");

        CoverType coverType = CoverType.create("cover", images);
        Letter letter = Letter.builder()
                .id(memberId)
                .receiverName("홍길동")
                .coverType(coverType)
                .deliveryDate(LocalDateTime.of(2024, 3, 1, 12, 0))
                .build();

        List<Letter> mockLetters = List.of(letter);

        when(memberDomainService.getParticipatedLetters(memberId)).thenReturn(mockLetters);

        // when
        ParticipationResponse response = memberService.getMemberParticipatedLetters(memberId);

        // then
        assertThat(response.getLetters()).hasSize(1);
        ParticipationResponse.LetterDto letterDto = response.getLetters().get(0);
        assertThat(letterDto.getLetterId()).isEqualTo(1L);
        assertThat(letterDto.getReceiverName()).isEqualTo("홍길동");
        assertThat(letterDto.getCoverTypeImage()).isEqualTo("url1");
    }

    @DisplayName("참여자가 참여한 편지 조회")
    @Test
    public void getMemberReceivedLettersTest() {
        // given
        Long memberId = 1L;
        CoverTypeImages images = new CoverTypeImages("url1", "url2", "url3", "url4", "url5", "url6", "url7");

        CoverType coverType = CoverType.create("cover", images);
        Letter letter = Letter.builder()
                .id(memberId)
                .receiverName("홍길동")
                .coverType(coverType)
                .deliveryDate(LocalDateTime.of(2024, 3, 1, 12, 0))
                .build();

        List<Letter> mockLetters = List.of(letter);

        when(memberDomainService.getReceivedLetters(memberId)).thenReturn(mockLetters);

        // when
        ReceivedLetterResponse response = memberService.getMemberReceivedLetters(memberId);

        // then
        assertThat(response.getLetters()).hasSize(1);
        ReceivedLetterResponse.LetterDto letterDto = response.getLetters().get(0);
        assertThat(letterDto.getLetterId()).isEqualTo(1L);
        assertThat(letterDto.getCoverTypeImage()).isEqualTo("url1");
    }


}
