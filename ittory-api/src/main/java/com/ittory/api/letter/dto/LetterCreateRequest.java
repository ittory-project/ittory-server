package com.ittory.api.letter.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class LetterCreateRequest {
    @Schema(description = "표지사진 아이디", example = "1", required = true)
    private Long coverTypeId;
    @Schema(description = "폰트 아이디", example = "1", required = true)
    private Long fontId;
    @Schema(description = "받는사람 아이디", example = "1")
    private Long receiverId;
    @Schema(description = "받는사람 이름", example = "김선재", required = true)
    private String receiverName;
    private LocalDateTime deliveryDate;
    @Schema(description = "제목", example = "선재야 생일 축하해!", required = true)
    private String title;
    @Schema(description = "표지사진 URL", example = "http://ittory..com/cover")
    private String coverPhotoUrl;
}
