package com.ittory.api.member.dto;

import com.ittory.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberSearchResponse {
    private Long id;
    private Long socialId;
    private String name;
    private String refreshToken;

    public static MemberSearchResponse from(Member member) {
        return MemberSearchResponse.builder()
                .id(member.getId())
                .socialId(member.getSocialId())
                .name(member.getName())
                .refreshToken(member.getRefreshToken())
                .build();
    }
}
