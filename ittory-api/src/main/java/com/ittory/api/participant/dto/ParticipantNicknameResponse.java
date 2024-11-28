package com.ittory.api.participant.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipantNicknameResponse {

    private Boolean isSuccess;
    private String nickname;

    public static ParticipantNicknameResponse of(Boolean isSuccess, String nickname) {
        return ParticipantNicknameResponse.builder()
                .isSuccess(isSuccess)
                .nickname(nickname)
                .build();
    }

}
