package com.ittory.api.letter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LetterRepeatCountRequest {
    @Schema(description = "편지 아이디", example = "1")
    private Long letterId;
    @Schema(description = "반복 횟수", example = "4")
    private int repeatCount;
}
