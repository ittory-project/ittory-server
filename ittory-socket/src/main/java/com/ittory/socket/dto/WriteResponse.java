package com.ittory.socket.dto;

import com.ittory.domain.letter.domain.Element;
import com.ittory.socket.enums.ActionType;
import lombok.*;

import static com.ittory.socket.enums.ActionType.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WriteResponse {

    private ActionType actionType;
    private ElementResponse completedElement;
    private ElementResponse upcomingElement;


    public static WriteResponse of(Element completedElement, Element upcomingElement) {
        return WriteResponse.builder()
                .actionType(WRITE)
                .completedElement(ElementResponse.of(completedElement))
                .upcomingElement(upcomingElement != null ? ElementResponse.of(upcomingElement) : null)
                .build();
    }

}
