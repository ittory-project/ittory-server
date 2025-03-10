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
public class MemberCreateResponse {
    private Long id;
    private Long socialId;
    private String name;

    public static MemberCreateResponse from(Member member) {
        return MemberCreateResponse.builder()
                .id(member.getId())
                .socialId(member.getSocialId())
                .name(member.getName())
                .build();
    }
}
