package com.ittory.socket.dto;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.participant.domain.Participant;
import com.ittory.socket.enums.WriteAction;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubmitResponse {

    private WriteAction actionType;
    private ElementResponse completedElement;
    private ElementResponse upcomingElement;


    public static SubmitResponse of(Element completedElement, Element upcomingElement) {
        return SubmitResponse.builder()
                .actionType(WriteAction.SUBMIT)
                .completedElement(ElementResponse.of(completedElement))
                .upcomingElement(upcomingElement != null ? ElementResponse.of(upcomingElement) : null)
                .build();
    }

}
