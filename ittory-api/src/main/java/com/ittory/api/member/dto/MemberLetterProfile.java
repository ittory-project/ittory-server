package com.ittory.api.member.dto;

import com.ittory.domain.member.domain.Participant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberLetterProfile {

    private Long memberId;
    private String nickname;
    private String imageUrl;

    public static MemberLetterProfile from(Participant participant) {
        return MemberLetterProfile.builder()
                .memberId(participant.getMember().getId())
                .nickname(participant.getNickname())
                .imageUrl(participant.getMember().getProfileImage())
                .build();
    }

}
