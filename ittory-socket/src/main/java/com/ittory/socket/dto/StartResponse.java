package com.ittory.socket.dto;

import com.ittory.socket.enums.ActionType;
import lombok.*;

import static com.ittory.socket.enums.ActionType.START;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StartResponse {

    private Long letterId;
    private ActionType action;

    public static StartResponse from(Long letterId) {
        return StartResponse.builder()
                .letterId(letterId)
                .action(START)
                .build();
    }

}
