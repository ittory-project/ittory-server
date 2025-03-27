package com.ittory.api.member.service;

import com.ittory.api.member.dto.LetterBoxParticipationRequest;
import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.enums.LetterStatus;
import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.service.LetterBoxDomainService;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.enums.ParticipantStatus;
import com.ittory.domain.participant.service.ParticipantDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class LetterBoxServiceTest {

    @MockBean
    private LetterBoxDomainService letterBoxDomainService;

    @MockBean
    private LetterDomainService letterDomainService;

    @MockBean
    private ParticipantDomainService participantDomainService;

    @Autowired
    private LetterBoxService letterBoxService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @DisplayName("편지함에서 편지 삭제")
    @Test
    void deleteLetterInLetterBoxTest() {
        // given
        Long memberId = 1L;
        Long letterId = 1L;

        // when
        letterBoxService.deleteLetterInLetterBox(memberId, letterId);

        // then
        verify(letterBoxDomainService).deleteByMemberIdAndLetterId(memberId, letterId);
    }

    @DisplayName("참여자 편지함에 편지 저장")
    @Test
    void saveInParticipationLetterBoxTest() {
        // given
        Long letterId = 1L;
        LetterBoxParticipationRequest request = new LetterBoxParticipationRequest(letterId);
        Letter letter = Letter.builder().id(letterId).build();
        Participant participant1 = Participant.builder().participantStatus(ParticipantStatus.PROGRESS).build();
        Participant participant2 = Participant.builder().participantStatus(ParticipantStatus.COMPLETED).build();
        List<Participant> participants = Arrays.asList(participant1, participant2);

        // when
        when(letterDomainService.findLetter(letterId)).thenReturn(letter);
        when(participantDomainService.findAllParticipants(letterId)).thenReturn(participants);

        letterBoxService.saveInParticipationLetterBox(request);

        // then
        verify(letterBoxDomainService).saveAllInParticipationLetterBox(participants, letter);
        verify(participantDomainService).updateAllStatusToEnd(letterId);
        verify(participantDomainService).saveAllParticipant(participants);
        verify(letterDomainService).updateLetterStatus(letterId, LetterStatus.COMPLETED);
    }

}