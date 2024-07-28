package com.ittory.api.cover.dto;

import com.ittory.domain.letter.domain.CoverType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
                .url(coverType.getUrl())
                .createdAt(coverType.getCreatedAt())
                .updatedAt(coverType.getUpdatedAt())
                .build();
    }
}