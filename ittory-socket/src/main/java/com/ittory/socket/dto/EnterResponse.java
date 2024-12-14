package com.ittory.socket.dto;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.enums.ConnectAction;
import lombok.*;

import java.util.List;

import static com.ittory.socket.enums.ConnectAction.ENTER;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EnterResponse {

    private Long participantId;
    private String nickname;
    private String imageUrl;
    private ConnectAction action;
    private List<ParticipantProfile> participants;

    public static EnterResponse from(Participant participant, List<ParticipantProfile> participants) {
        return EnterResponse.builder()
                .participantId(participant.getId())
                .nickname(participant.getNickname())
                .imageUrl(participant.getMember().getProfileImage())
                .participants(participants)
                .action(ENTER)
                .build();
    }
}
