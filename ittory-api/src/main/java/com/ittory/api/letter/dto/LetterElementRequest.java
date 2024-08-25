package com.ittory.api.letter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LetterElementRequest {
    @Schema(description = "편지 내용", example = "나는 지금 너를 위해 폰으로 릴레이 편지를 쓰고 있다!")
    private String content;
}
