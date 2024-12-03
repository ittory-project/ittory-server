package com.ittory.api.letter.dto;

import com.ittory.domain.participant.enums.EnterAction;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LetterEnterStatusResponse {

    private Boolean enterStatus;
    private EnterAction enterAction;
    private Long participantId;


    // Deprecated
    public static LetterEnterStatusResponse of(Boolean enterStatus) {
        return LetterEnterStatusResponse.builder()
                .enterStatus(enterStatus)
                .build();
    }

    public static LetterEnterStatusResponse of(Boolean enterStatus, EnterAction enterAction, Long participantId) {
        return LetterEnterStatusResponse.builder()
                .enterStatus(enterStatus)
                .enterAction(enterAction)
                .participantId(participantId)
                .build();
    }

}
