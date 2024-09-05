package com.ittory.api.participant.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class NicknameDuplicationResultResponse {

    private Boolean isDuplicate;

    public static NicknameDuplicationResultResponse from(Boolean isDuplicate) {
        return NicknameDuplicationResultResponse.builder()
                .isDuplicate(isDuplicate)
                .build();
    }

}
