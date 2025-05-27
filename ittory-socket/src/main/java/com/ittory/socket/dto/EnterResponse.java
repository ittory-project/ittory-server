package com.ittory.socket.dto;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.enums.ActionType;
import lombok.*;

import java.util.List;

import static com.ittory.socket.enums.ActionType.ENTER;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EnterResponse {

    private Long memberId;
    private String nickname;
    private String imageUrl;
    private ActionType action;
    private List<ParticipantProfile> participants;

    public static EnterResponse from(Participant participant, List<ParticipantProfile> participants) {
        return EnterResponse.builder()
                .memberId(participant.getMember().getId())
                .nickname(participant.getNickname())
                .imageUrl(participant.getMember().getProfileImage())
                .participants(participants)
                .action(ENTER)
                .build();
    }
}
