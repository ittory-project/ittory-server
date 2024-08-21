package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Font;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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