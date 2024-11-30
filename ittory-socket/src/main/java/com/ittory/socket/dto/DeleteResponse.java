package com.ittory.socket.dto;

import com.ittory.socket.enums.ProcessAction;
import lombok.*;

import static com.ittory.socket.enums.ProcessAction.DELETE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteResponse {

    private Long letterId;
    private ProcessAction action;

    public static DeleteResponse from(Long letterId) {
        return DeleteResponse.builder()
                .letterId(letterId)
                .action(DELETE)
                .build();
    }

}
