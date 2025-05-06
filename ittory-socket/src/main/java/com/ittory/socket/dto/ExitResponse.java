package com.ittory.socket.dto;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.enums.ConnectAction;
import lombok.*;

import java.util.List;

import static com.ittory.socket.enums.ConnectAction.EXIT;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExitResponse {

    private ConnectAction actionType;
    private Long exitMemberId;
    private Boolean isManager;
    private List<ParticipantProfile> nowParticipants;

    public static ExitResponse from(Participant participant, Boolean isManager, List<ParticipantProfile> nowParticipants) {
        return ExitResponse.builder()
                .actionType(EXIT)
                .exitMemberId(participant.getMember().getId())
                .isManager(isManager)
                .nowParticipants(nowParticipants)
                .build();
    }

}
