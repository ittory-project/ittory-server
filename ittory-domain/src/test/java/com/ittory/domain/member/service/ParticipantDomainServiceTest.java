package com.ittory.domain.member.service;

import static com.ittory.domain.member.enums.ParticipantStatus.EXITED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.domain.Participant;
import com.ittory.domain.member.exception.ParticipantException.ParticipantNotFoundException;
import com.ittory.domain.member.repository.MemberRepository;
import com.ittory.domain.member.repository.ParticipantRepository;
import java.util.List;
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
        participantRepository.save(Participant.create(member, letter));

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
        participantRepository.save(Participant.create(member, letter));

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
        participantRepository.save(Participant.create(member, letter));

        //when & then
        assertThatThrownBy(() -> participantDomainService.findParticipant(letter.getId(), -1L))
                .isInstanceOf(ParticipantNotFoundException.class);
    }

    @DisplayName("편지에 현재 참여중인 참여자를 조회한다.")
    @Test
    void findAllCurrentParticipantTest() {
        //given
        Member member1 = memberRepository.save(Member.create(1L, "tester1", null));
        Member member2 = memberRepository.save(Member.create(2L, "tester2", null));
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());
        Participant participant1 = Participant.create(member1, letter);
        Participant participant2 = Participant.create(member2, letter);
        participant2.changeParticipantStatus(EXITED);
        participantRepository.saveAll(List.of(participant1, participant2));

        //when
        List<Participant> participants = participantDomainService.findAllCurrentParticipant(letter.getId());

        //then
        assertThat(participants.size()).isEqualTo(1);
    }

}