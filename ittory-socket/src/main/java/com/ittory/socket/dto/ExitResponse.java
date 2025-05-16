package com.ittory.socket.dto;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.enums.ActionType;
import lombok.*;

import static com.ittory.socket.enums.ActionType.EXIT;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExitResponse {

    private ActionType action;
    private Long exitMemberId;
    private Boolean isManager;

    public static ExitResponse from(Participant participant, Boolean isManager) {
        return ExitResponse.builder()
                .action(EXIT)
                .exitMemberId(participant.getMember().getId())
                .isManager(isManager)
                .build();
    }

}
