package com.ittory.api.member.dto;

import com.ittory.domain.member.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberDetailResponse {
    private Long memberId;
    private String name;
    private String profileImage;
    private String memberStatus;

    public static MemberDetailResponse from(Member member) {
        return MemberDetailResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .profileImage(member.getProfileImage())
                .memberStatus(String.valueOf(member.getMemberStatus()))
                .build();
    }
}
