package com.ittory.domain.participant.service;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.LetterRepository;
import com.ittory.domain.member.domain.Member;
import com.ittory.domain.member.repository.MemberRepository;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.exception.ParticipantException.ParticipantNotFoundException;
import com.ittory.domain.participant.repository.ParticipantRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static com.ittory.domain.participant.enums.ParticipantStatus.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


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
    void findAllCurrentParticipantsTest() {
        //given
        Member member1 = memberRepository.save(Member.create(1L, "tester1", null));
        Member member2 = memberRepository.save(Member.create(2L, "tester2", null));
        Member member3 = memberRepository.save(Member.create(3L, "tester3", null));
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());
        Participant participant1 = Participant.create(member1, letter);
        Participant participant2 = Participant.create(member2, letter);
        Participant participant3 = Participant.create(member3, letter);
        participant1.changeParticipantStatus(PROGRESS);
        participant1.changeParticipantStatus(ENTER);
        participant2.changeParticipantStatus(EXITED);
        participantRepository.saveAll(List.of(participant1, participant2));

        //when
        List<Participant> participants = participantDomainService.findAllCurrentParticipantsOrderedBySequence(
                letter.getId(), null);

        //then
        assertThat(participants.size()).isEqualTo(1);
    }

    @DisplayName("편지에 현재 참여중인 참여자를 순서에 맞게 조회한다.")
    @Test
    void findAllCurrentParticipantsOrderedBySequenceTest() {
        //given
        Member member1 = memberRepository.save(Member.create(1L, "tester1", null));
        Member member2 = memberRepository.save(Member.create(2L, "tester2", null));
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());
        Participant participant1 = Participant.create(member1, letter);
        Participant participant2 = Participant.create(member2, letter);
        participant1.changeNickname("participant1");
        participant2.changeNickname("participant2");
        participant1.changeParticipantStatus(PROGRESS);
        participant2.changeParticipantStatus(PROGRESS);

        participant1.changeSequence(2);
        participant2.changeSequence(1);
        participantRepository.saveAll(List.of(participant1, participant2));

        //when
        List<Participant> participants = participantDomainService.findAllCurrentParticipantsOrderedBySequence(
                letter.getId(), true);

        //then
        assertThat(participants.get(0).getNickname()).isEqualTo("participant2");
    }

    @DisplayName("참여자 퇴장 테스트.")
    @Test
    void exitParticipantTest() {
        //given
        Member member = memberRepository.save(Member.create(1L, "tester1", null));
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());
        Participant newParticipant = Participant.create(member, letter);
        Participant savedParticipant = participantRepository.save(newParticipant);

        //when
        participantDomainService.exitParticipant(newParticipant);
        Participant participant = participantRepository.findById(savedParticipant.getId()).orElse(null);

        //then
        assertThat(participant).isNotNull();
        assertThat(participant.getParticipantStatus()).isEqualTo(EXITED);
    }

    @DisplayName("참여자의 순서를 변경한다.")
    @Test
    void reorderParticipantsAfterLeaveTest() {
        //given
        Member member1 = memberRepository.save(Member.create(1L, "tester1", null));
        Member member2 = memberRepository.save(Member.create(2L, "tester2", null));
        Member member3 = memberRepository.save(Member.create(2L, "tester2", null));
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());
        Participant participant1 = Participant.create(member1, letter);
        Participant participant2 = Participant.create(member2, letter);
        Participant participant3 = Participant.create(member3, letter);
        participant1.changeSequence(1);
        participant2.changeSequence(2);
        participant3.changeSequence(3);
        participant1.changeParticipantStatus(EXITED);
        participant2.changeParticipantStatus(PROGRESS);
        participant3.changeParticipantStatus(PROGRESS);
        participantRepository.saveAll(List.of(participant1, participant2, participant3));

        //when
        participantDomainService.reorderParticipantsAfterLeave(letter.getId(), participant1);

        //then
        List<Participant> currentParticipant = participantRepository.findCurrentParticipantsByLetterIdOrdered(
                letter.getId(), null);
        assertThat(currentParticipant.get(0).getSequence()).isEqualTo(1);
        assertThat(currentParticipant.get(1).getSequence()).isEqualTo(2);
    }

    @DisplayName("참여자의 닉네임이 중복인지 확인한다.")
    @Test
    void checkNicknameDuplicationTest() {
        //given
        Member member1 = memberRepository.save(Member.create(1L, "tester1", null));
        Member member2 = memberRepository.save(Member.create(2L, "tester2", null));
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());

        Participant enterParticipant = Participant.create(member1, letter);
        enterParticipant.changeNickname("participant");
        enterParticipant.changeParticipantStatus(ENTER);
        participantRepository.save(enterParticipant);

        Participant ghostParticipant = Participant.create(member2, letter);
        ghostParticipant.changeNickname("ghost");
        ghostParticipant.changeParticipantStatus(GHOST);
        participantRepository.save(ghostParticipant);


        //when
        Boolean isEnterDuplicate = participantDomainService.checkNicknameDuplication(letter.getId(), "participant");
        Boolean isGhostDuplicate = participantDomainService.checkNicknameDuplication(letter.getId(), "ghost");

        //then
        assertThat(isEnterDuplicate).isTrue();
        assertThat(isGhostDuplicate).isFalse();
    }

    @DisplayName("편지의 참여자가 5명 미만인지 체크한다.")
    @Test
    void getEnterStatusTest() {
        //given
        int MAX_PARTICIPANT_SIZE = 5;
        List<Member> newMembers = new ArrayList<>();
        for (int i = 0; i < MAX_PARTICIPANT_SIZE; i++) {
            Member member = Member.create((long) i, "tester" + i, null);
            newMembers.add(member);
        }
        List<Member> members = memberRepository.saveAll(newMembers);
        Letter letter = letterRepository.save(Letter.builder().title("test_letter").build());

        List<Participant> newParticipants = new ArrayList<>();
        for (int i = 0; i < MAX_PARTICIPANT_SIZE; i++) {
            Participant participant = Participant.create(members.get(i), letter);
            participant.changeParticipantStatus(ENTER);
            newParticipants.add(participant);
        }
        participantRepository.saveAll(newParticipants);

        //when
        Boolean enterStatus = participantDomainService.getEnterStatus(letter.getId());

        //then
        assertThat(enterStatus).isFalse();
    }

}
