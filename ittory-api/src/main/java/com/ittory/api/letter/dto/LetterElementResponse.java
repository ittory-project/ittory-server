package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.LetterElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LetterElementResponse {
    private Long id;
    private String content;

    public LetterElementResponse(Long id, String content) {
        this.id = id;
        this.content = content;
    }

    public static LetterElementResponse from(LetterElement letterElement) {
        return new LetterElementResponse(
                letterElement.getId(),
                letterElement.getContent()
        );
    }
}
