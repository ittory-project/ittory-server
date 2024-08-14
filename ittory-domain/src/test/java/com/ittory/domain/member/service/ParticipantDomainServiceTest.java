package com.ittory.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.exception.ParticipantException.ParticipantNotFoundException;
import com.ittory.domain.member.repository.MemberRepository;
import com.ittory.domain.member.repository.ParticipantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class ParticipantDomainServiceTest {

    @Autowired
    private ParticipantDomainService participantDomainService;

    @Autowired
    private ParticipantRepository participantRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private LetterRepository letterRepository;

    @AfterEach
    void clean() {
        participantRepository.deleteAll();
        letterRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @DisplayName("참여자를 편지와 사용자 정보로 조회한다.")
    @Test
    void findByLetterIdAndMemberIdTest() {
        //given
        Member member = memberRepository.save(Member.create(1L, "tester", null));
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());
        participantRepository.save(Participant.create(member, letter, 0));

        //when
        Participant participant = participantDomainService.findParticipant(letter.getId(), member.getId());

        //then
        assertThat(participant.getMember().getName()).isEqualTo("tester");
        assertThat(participant.getLetter().getTitle()).isEqualTo("test_letter");
    }

    @DisplayName("참여자를 찾을 때 잘못된 편지 정보로 예외가 발생한다.")
    @Test
    void findByLetterIdAndMemberIdTest_ParticipantNotFoundException_Letter() {
        //given
        Member member = memberRepository.save(Member.create(1L, "tester", null));
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());
        participantRepository.save(Participant.create(member, letter, 0));

        //when & then
        assertThatThrownBy(() -> participantDomainService.findParticipant(-1L, member.getId()))
                .isInstanceOf(ParticipantNotFoundException.class);

    }

    @DisplayName("참여자를 찾을 때 잘못된 사용자 정보로 예외가 발생한다.")
    @Test
    void findByLetterIdAndMemberIdTest_ParticipantNotFoundException_Member() {
        //given
        Member member = memberRepository.save(Member.create(1L, "tester", null));
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());
        participantRepository.save(Participant.create(member, letter, 0));

        //when & then
        assertThatThrownBy(() -> participantDomainService.findParticipant(letter.getId(), -1L))
                .isInstanceOf(ParticipantNotFoundException.class);
    }

}
