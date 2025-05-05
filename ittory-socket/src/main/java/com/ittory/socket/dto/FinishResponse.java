package com.ittory.socket.dto;

import com.ittory.socket.enums.ProcessAction;
import lombok.*;

import static com.ittory.socket.enums.ProcessAction.FINISH;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FinishResponse {

    private ProcessAction action;

    public static FinishResponse from() {
        return FinishResponse.builder()
                .action(FINISH)
                .build();
    }

}
