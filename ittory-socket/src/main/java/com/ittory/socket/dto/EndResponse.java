package com.ittory.socket.dto;

import com.ittory.socket.enums.ProcessAction;
import lombok.*;

import static com.ittory.socket.enums.ProcessAction.END;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class EndResponse {

    private Long letterId;
    private ProcessAction action;

    public static EndResponse from(Long letterId) {
        return EndResponse.builder()
                .letterId(letterId)
                .action(END)
                .build();
    }

}
