package com.ittory.domain.participant.domain;

import static com.ittory.domain.participant.enums.ParticipantStatus.EXITED;
import static com.ittory.domain.participant.enums.ParticipantStatus.PROGRESS;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class ParticipantTest {

    @DisplayName("참여자의 순서 변경 기능")
    @Test
    void changeSortTest() {
        // given
        Participant participant = Participant.builder().build();

        // when
        participant.changeSort(1);

        // then
        Assertions.assertThat(participant.getSort()).isEqualTo(1);
    }

    @DisplayName("참여자의 상태 변경 기능")
    @Test
    void changeParticipantStatusTest() {
        // given
        Participant participant = Participant.builder().participantStatus(PROGRESS).build();

        // when
        participant.changeParticipantStatus(EXITED);

        // then
        Assertions.assertThat(participant.getParticipantStatus()).isEqualTo(EXITED);
    }

}
