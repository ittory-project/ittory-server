package com.ittory.socket.dto;

import com.ittory.domain.letter.domain.Element;
import com.ittory.socket.enums.ActionType;
import lombok.*;

import static com.ittory.socket.enums.ActionType.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SubmitResponse {

    private ActionType actionType;
    private ElementResponse completedElement;
    private ElementResponse upcomingElement;


    public static SubmitResponse of(Element completedElement, Element upcomingElement) {
        return SubmitResponse.builder()
                .actionType(SUBMIT)
                .completedElement(ElementResponse.of(completedElement))
                .upcomingElement(upcomingElement != null ? ElementResponse.of(upcomingElement) : null)
                .build();
    }

}
