package com.ittory.socket.dto;

import com.ittory.socket.enums.ProcessAction;
import lombok.*;

import static com.ittory.socket.enums.ProcessAction.START;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class StartResponse {

    private Long letterId;
    private ProcessAction action;

    public static StartResponse from(Long letterId) {
        return StartResponse.builder()
                .letterId(letterId)
                .action(START)
                .build();
    }

}
