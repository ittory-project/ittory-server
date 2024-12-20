package com.ittory.api.letter.dto;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class LetterDetailResponse {
    private Long letterId;
    private Long coverTypeId;
    private Long fontId;
    private String receiverName;
    private LocalDateTime deliveryDate;
    private String title;
    private String coverPhotoUrl;
    private List<String> participantNames;
    private List<LetterElementResponse> elements;

    public static LetterDetailResponse of(Letter letter, List<String> participantNames, List<Element> elements) {
        return LetterDetailResponse.builder()
                .letterId(letter.getId())
                .coverTypeId(letter.getCoverType().getId())
                .fontId(letter.getFont().getId())
                .receiverName(letter.getReceiverName())
                .deliveryDate(letter.getDeliveryDate())
                .title(letter.getTitle())
                .coverPhotoUrl(letter.getCoverPhotoUrl())
                .participantNames(participantNames)
                .elements(elements.stream()
                        .map(LetterElementResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
