package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Element;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementSimpleResponse {

    private Long elementId;
    private String imageUrl;
    private String content;
    private LocalDateTime startedAt;
    private Long memberId;
    private String nickname;

    public static ElementSimpleResponse from(Element element) {
        Long memberId = element.getParticipant() != null ? element.getParticipant().getMember().getId() : null;
        String nickname = element.getParticipant() != null ? element.getParticipant().getNickname() : null;
        return ElementSimpleResponse.builder()
                .elementId(element.getId())
                .imageUrl(element.getElementImage().getUrl())
                .content(element.getContent())
                .startedAt(element.getStartTime())
                .memberId(memberId)
                .nickname(nickname)
                .build();
    }

}
