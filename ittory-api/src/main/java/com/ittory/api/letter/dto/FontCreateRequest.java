package com.ittory.api.letter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FontCreateRequest {
    @Schema(description = "폰트 이름", example = "맑은고딕", required = true)
    private String name;

    @Schema(description = "폰트 벨류", example = "GmarketSans", required = true)
    private String value;
}