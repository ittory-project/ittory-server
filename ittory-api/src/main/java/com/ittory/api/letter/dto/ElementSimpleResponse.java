package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Element;
import lombok.*;

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
        String nickname = element.getParticipant() != null ? element.getParticipant().getNickname() : null;
        return ElementSimpleResponse.builder()
                .elementId(element.getId())
                .nickname(nickname)
                .imageUrl(element.getElementImage().getUrl())
                .content(element.getContent())
                .sequence(element.getSequence())
                .build();
    }

}
