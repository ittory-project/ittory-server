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
public class NicknameDuplicationResponse {

    private Boolean isDuplicate;

    public static NicknameDuplicationResponse from(Boolean isDuplicate) {
        return NicknameDuplicationResponse.builder()
                .isDuplicate(isDuplicate)
                .build();
    }

}
