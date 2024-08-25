package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.domain.LetterElement;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LetterDetailResponse {
    private Long letterId;
    private String receiverName;
    private LocalDateTime deliveryDate;
    private String title;
    private String coverPhotoUrl;
    private List<LetterElementResponse> elements;

    public static LetterDetailResponse from(Letter letter, List<LetterElement> elements) {
        return LetterDetailResponse.builder()
                .letterId(letter.getId())
                .receiverName(letter.getReceiverName())
                .deliveryDate(letter.getDeliveryDate())
                .title(letter.getTitle())
                .coverPhotoUrl(letter.getCoverPhotoUrl())
                .elements(elements.stream()
                        .map(LetterElementResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
