package com.ittory.api.letter.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LetterEnterStatusResponse {

    private Boolean enterStatus;

    public static LetterEnterStatusResponse of(Boolean enterStatus) {
        return LetterEnterStatusResponse.builder()
                .enterStatus(enterStatus)
                .build();
    }

}
