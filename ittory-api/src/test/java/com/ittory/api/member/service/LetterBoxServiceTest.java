package com.ittory.api.member.service;

import com.ittory.domain.letter.service.LetterDomainService;
import com.ittory.domain.member.service.LetterBoxDomainService;
import com.ittory.domain.participant.service.ParticipantDomainService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

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

}