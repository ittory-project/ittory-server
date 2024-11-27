package com.ittory.api.participant.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ParticipantNicknameRequest {

    private Long letterId;
    private String nickname;

}
