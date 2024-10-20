package com.ittory.socket.dto;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.participant.domain.Participant;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementResponse {

    private Long elementId;
    private String imageUrl;
    private String content;
    private String nickname;
    private Integer elementSequence;
    private Integer writeSequence;


    public static ElementResponse of(Participant participant, Element element) {
        return ElementResponse.builder()
                .elementId(element.getId())
                .imageUrl(element.getElementImage().getUrl())
                .content(element.getContent())
                .nickname(participant.getNickname())
                .elementSequence(element.getSequence())
                .writeSequence(participant.getSequence())
                .build();
    }

}
