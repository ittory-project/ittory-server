package com.ittory.api.letter.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LetterEnterStatusResponse {

    private Boolean enterStatus;
    private Long participantId;


    // Deprecated
    public static LetterEnterStatusResponse of(Boolean enterStatus) {
        return LetterEnterStatusResponse.builder()
                .enterStatus(enterStatus)
                .build();
    }

    public static LetterEnterStatusResponse of(Boolean enterStatus, Long participantId) {
        return LetterEnterStatusResponse.builder()
                .enterStatus(enterStatus)
                .participantId(participantId)
                .build();
    }

}
