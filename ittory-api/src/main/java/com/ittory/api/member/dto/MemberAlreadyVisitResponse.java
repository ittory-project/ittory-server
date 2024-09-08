package com.ittory.api.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberAlreadyVisitResponse {

    private Boolean isVisited;

    public static MemberAlreadyVisitResponse from(Boolean isVisited) {
        return MemberAlreadyVisitResponse.builder()
                .isVisited(isVisited)
                .build();
    }

}
