package com.ittory.socket.dto;

import com.ittory.socket.enums.ActionType;
import lombok.*;

import static com.ittory.socket.enums.ActionType.DELETE;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteResponse {

    private Long letterId;
    private ActionType action;

    public static DeleteResponse from(Long letterId) {
        return DeleteResponse.builder()
                .letterId(letterId)
                .action(DELETE)
                .build();
    }

}
