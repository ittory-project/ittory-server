package com.ittory.socket.dto;

import com.ittory.socket.enums.ActionType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SimpleResponse {

    private ActionType action;

    public static SimpleResponse from(ActionType actionType) {
        return SimpleResponse.builder()
                .action(actionType)
                .build();
    }

}
