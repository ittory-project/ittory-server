package com.ittory.api.letter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoverTypeCreateRequest {
    @Schema(description = "표지사진 이름", example = "Cover_photo", required = true)
    private String name;

    @Schema(description = "편지함 표지 타입의 URL", example = "http://ittory..com/cover")
    private String listImageUrl;

    @Schema(description = "표지 선택 시 간단사진의 URL", example = "http://ittory..com/cover")
    private String selectImageUrl;

    @Schema(description = "선택되지 않은 표지의 간단사진의 URL", example = "http://ittory..com/cover")
    private String notSelectImageUrl;

    @Schema(description = "표지 꾸미기 시 배경 이미지 URL", example = "http://ittory..com/cover")
    private String editImageUrl;

    @Schema(description = "표지 확인 시 배경 이미지 URL", example = "http://ittory..com/cover")
    private String confirmImageUrl;

    @Schema(description = "편지 완성본의 표지 배경 이미지 URL", example = "http://ittory..com/cover")
    private String outputBackgroundImageUrl;

    @Schema(description = "편지 작성 로딩 시 표지 배경 이미지 URL", example = "http://ittory..com/cover")
    private String loadingBackgroundImageUrl;

}