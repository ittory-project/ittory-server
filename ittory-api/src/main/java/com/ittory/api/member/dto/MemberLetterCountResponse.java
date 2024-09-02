package com.ittory.api.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLetterCountResponse {

    private Integer participationLetterCount;
    private Integer receiveLetterCount;

    public static MemberLetterCountResponse of(Integer participationLetterCount, Integer receiveLetterCount) {
        return MemberLetterCountResponse.builder()
                .participationLetterCount(participationLetterCount)
                .receiveLetterCount(receiveLetterCount)
                .build();
    }

}
