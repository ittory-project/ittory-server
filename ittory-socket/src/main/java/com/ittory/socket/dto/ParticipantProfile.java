package com.ittory.socket.dto;

import com.ittory.domain.participant.domain.Participant;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipantProfile {

    private Integer sequence;
    private Long memberId;
    private String nickname;
    private String imageUrl;

    public static ParticipantProfile from(Participant participant) {
        return ParticipantProfile.builder()
                .sequence(participant.getSequence())
                .memberId(participant.getMember().getId())
                .nickname(participant.getNickname())
                .imageUrl(participant.getMember().getProfileImage())
                .build();
    }

}
