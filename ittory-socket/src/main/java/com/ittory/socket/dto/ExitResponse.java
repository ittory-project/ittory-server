package com.ittory.socket.dto;

import static com.ittory.socket.enums.ConnectAction.EXIT;

import com.ittory.domain.member.domain.Participant;
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

    private Long memberId;
    private String name;
    private ConnectAction action;

    public static ExitResponse from(Participant participant) {
        return ExitResponse.builder()
                .memberId(participant.getMember().getId())
                .name(participant.getNickname())
                .action(EXIT)
                .build();
    }

}
