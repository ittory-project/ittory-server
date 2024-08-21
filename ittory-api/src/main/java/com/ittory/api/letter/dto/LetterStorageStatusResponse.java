package com.ittory.api.letter.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class LetterStorageStatusResponse {

    private Boolean isStored;
    private String receiverName;

    public static LetterStorageStatusResponse of(Boolean isStored, String receiverName) {
        return LetterStorageStatusResponse.builder()
                .isStored(isStored)
                .receiverName(receiverName)
                .build();
    }

}
