package com.ittory.api.letter.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LetterStartInfoResponse {

    private String title;
    private Integer participantCount;
    private Integer repeatCount;
    private Integer elementCount;

    public static LetterStartInfoResponse of(String title, Integer participantCount,
                                             Integer repeatCount, Integer elementCount) {
        return LetterStartInfoResponse.builder()
                .title(title)
                .participantCount(participantCount)
                .repeatCount(repeatCount)
                .elementCount(elementCount)
                .build();
    }

}
