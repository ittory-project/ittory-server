package com.ittory.socket.dto;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.enums.ConnectAction;
import lombok.*;

import static com.ittory.socket.enums.ConnectAction.EXIT;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExitResponse {

    private Long participantId;
    private String nickname;
    private ConnectAction action;
    private Boolean isManager;

    public static ExitResponse from(Participant participant, Boolean isManager) {
        return ExitResponse.builder()
                .participantId(participant.getId())
                .nickname(participant.getNickname())
                .action(EXIT)
                .isManager(isManager)
                .build();
    }

}
