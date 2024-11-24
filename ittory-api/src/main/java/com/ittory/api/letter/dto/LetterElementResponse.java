package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Element;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LetterElementResponse {
    private Long id;
    private String coverImageUrl;
    private String content;

    public LetterElementResponse(Long id, String coverImageUrl, String content) {
        this.id = id;
        this.coverImageUrl = coverImageUrl;
        this.content = content;
    }

    public static LetterElementResponse from(Element letterElement) {
        return new LetterElementResponse(
                letterElement.getId(),
                letterElement.getElementImage().getUrl(),
                letterElement.getContent()
        );
    }
}
