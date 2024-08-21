package com.ittory.api.letter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoverTypeCreateRequest {
    @Schema(description = "표지사진 이름", example = "Cover_photo", required = true)
    private String name;

    @Schema(description = "표지사진의 URL", example = "http://ittory..com/cover")
    private String url;
}