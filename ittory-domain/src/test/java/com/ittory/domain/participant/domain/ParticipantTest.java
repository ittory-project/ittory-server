package com.ittory.domain.participant.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.ittory.domain.participant.enums.ParticipantStatus.GUEST;
import static com.ittory.domain.participant.enums.ParticipantStatus.PROGRESS;

@DataJpaTest
public class ParticipantTest {

    @DisplayName("참여자의 순서 변경 기능")
    @Test
    void changeSortTest() {
        // given
        Participant participant = Participant.builder().build();

        // when
        participant.changeSequence(1);

        // then
        Assertions.assertThat(participant.getSequence()).isEqualTo(1);
    }

    @DisplayName("참여자의 상태 변경 기능")
    @Test
    void changeParticipantStatusTest() {
        // given
        Participant participant = Participant.builder().participantStatus(PROGRESS).build();

        // when
        participant.changeParticipantStatus(GUEST);

        // then
        Assertions.assertThat(participant.getParticipantStatus()).isEqualTo(GUEST);
    }

}
