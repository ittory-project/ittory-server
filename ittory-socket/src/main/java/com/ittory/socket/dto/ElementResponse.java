package com.ittory.socket.dto;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.participant.domain.Participant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementResponse {

    private Long elementId;
    private Integer sequence;
    private String imageUrl;
    private String content;
    private String nickname;


    public static ElementResponse of(Participant participant, Element element) {
        return ElementResponse.builder()
                .elementId(element.getId())
                .sequence(element.getSequence())
                .imageUrl(element.getElementImage().getUrl())
                .content(element.getContent())
                .nickname(participant.getNickname())
                .build();
    }

}
