package com.ittory.socket.dto;

import static com.ittory.socket.enums.ConnectAction.EXIT;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.enums.ConnectAction;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExitResponse {

    private Long participantId;
    private String nickname;
    private ConnectAction action;

    public static ExitResponse from(Participant participant) {
        return ExitResponse.builder()
                .participantId(participant.getId())
                .nickname(participant.getNickname())
                .action(EXIT)
                .build();
    }

}
