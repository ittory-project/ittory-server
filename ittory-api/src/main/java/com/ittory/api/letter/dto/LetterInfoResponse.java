package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Letter;
import lombok.*;

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

    public static LetterInfoResponse from(Letter letter) {
        return LetterInfoResponse.builder()
                .letterId(letter.getId())
                .receiverName(letter.getReceiverName())
                .deliveryDate(letter.getDeliveryDate())
                .title(letter.getTitle())
                .coverPhotoUrl(letter.getCoverPhotoUrl())
                .build();
    }
}