package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.CoverType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CoverTypeCreateResponse {
    private Long id;
    private String name;
    private String url;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static CoverTypeCreateResponse from(CoverType coverType) {
        return CoverTypeCreateResponse.builder()
                .id(coverType.getId())
                .name(coverType.getName())
                .url(coverType.getImageUrl())
                .createdAt(coverType.getCreatedAt())
                .updatedAt(coverType.getUpdatedAt())
                .build();
    }
}