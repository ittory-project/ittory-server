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
    private String imageUrl;

    @Schema(description = "표지 간단사진의 URL", example = "http://ittory..com/cover")
    private String simpleUrl;

    @Schema(description = "표지 배경 이미지 URL", example = "http://ittory..com/cover")
    private String backgroundUrl;


}