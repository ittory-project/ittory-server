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
public class LetterStartInfoResponse {

    private Integer participantCount;
    private Integer repeatCount;
    private Integer elementCount;

    public static LetterStartInfoResponse of(Integer participantCount, Integer repeatCount, Integer elementCount) {
        return LetterStartInfoResponse.builder()
                .participantCount(participantCount)
                .repeatCount(repeatCount)
                .elementCount(elementCount)
                .build();
    }

}
