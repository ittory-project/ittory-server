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
public class LetterInfoResponse {
    private Long letterId;
    private String receiverName;
    private LocalDateTime deliveryDate;
    private String title;
    private String coverPhotoUrl;
    private Long coverTypeId;
    private Long fontId;

    public static LetterInfoResponse from(Letter letter) {
        return LetterInfoResponse.builder()
                .letterId(letter.getId())
                .receiverName(letter.getReceiverName())
                .deliveryDate(letter.getDeliveryDate())
                .title(letter.getTitle())
                .coverPhotoUrl(letter.getCoverPhotoUrl())
                .coverTypeId(letter.getCoverType().getId())
                .fontId(letter.getFont().getId())
                .build();
    }
}