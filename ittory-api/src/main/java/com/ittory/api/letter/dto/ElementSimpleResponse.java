package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Element;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementSimpleResponse {

    private Long elementId;
    private String nickname;
    private String imageUrl;
    private String content;
    private Integer sequence;

    public static ElementSimpleResponse from(Element element) {
        return ElementSimpleResponse.builder()
                .elementId(element.getId())
                .nickname(element.getParticipant().getNickname())
                .imageUrl(element.getElementImage().getUrl())
                .content(element.getContent())
                .sequence(element.getSequence())
                .build();
    }

}
