package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Element;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LetterElementResponse {
    private Long id;
    private String nickname;
    private String coverImageUrl;
    private String content;
    private Integer sequence;

    public static LetterElementResponse from(Element letterElement) {
        String nickname = letterElement.getParticipant() != null ? letterElement.getParticipant().getNickname() : null;
        return new LetterElementResponse(
                letterElement.getId(),
                nickname,
                letterElement.getElementImage().getUrl(),
                letterElement.getContent(),
                letterElement.getSequence()
        );
    }
}
