package com.ittory.socket.dto;

import com.ittory.domain.letter.domain.Element;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementResponse {

    private Long elementId;
    private String imageUrl;
    private String content;
    private LocalDateTime startedAt;
    private Long memberId;
    private String nickname;

    public static ElementResponse of(Element element) {

        return ElementResponse.builder().
                elementId(element.getId())
                .imageUrl(element.getElementImage().getUrl())
                .content(element.getContent() != null ? element.getContent() : null)
                .startedAt(element.getStartTime() != null ? element.getStartTime() : null)
                .memberId(element.getParticipant() != null ? element.getParticipant().getMember().getId() : null)
                .nickname(element.getParticipant() != null ? element.getParticipant().getNickname() : null)
                .build();
    }

}
