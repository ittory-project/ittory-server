package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LetterCreateResponse {
    private Long letterId;
    private Long coverTypeId;
    private Long fontId;
    private String receiverName;
    private LocalDateTime deliveryDate;
    private String title;
    private String coverPhotoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public static LetterCreateResponse from(Letter letter) {
        return LetterCreateResponse.builder()
                .letterId(letter.getId())
                .coverTypeId(letter.getCoverType().getId())
                .fontId(letter.getFont().getId())
                .receiverName(letter.getReceiverName())
                .deliveryDate(letter.getDeliveryDate())
                .title(letter.getTitle())
                .coverPhotoUrl(letter.getCoverPhotoUrl())
                .createdAt(letter.getCreatedAt())
                .updatedAt(letter.getUpdatedAt())
                .build();
    }
}
