package com.ittory.api.font.dto;

import com.ittory.domain.letter.domain.Font;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FontCreateResponse {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static FontCreateResponse from(Font font) {
        return FontCreateResponse.builder()
                .id(font.getId())
                .name(font.getName())
                .createdAt(font.getCreatedAt())
                .updatedAt(font.getUpdatedAt())
                .build();
    }
}