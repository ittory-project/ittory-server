package com.ittory.domain.participant.dto;

import com.ittory.domain.participant.enums.EnterAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class EnterStatusResponse {

    private Boolean enterStatus;
    private EnterAction enterAction;

    public static EnterStatusResponse of(Boolean enterStatus, EnterAction enterAction) {
        return EnterStatusResponse.builder()
                .enterStatus(enterStatus)
                .enterAction(enterAction)
                .build();
    }
}
