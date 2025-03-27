package com.ittory.api.member.dto;

import com.ittory.domain.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReceivedLetterResponse {
    private List<LetterDto> letters;

    public static ReceivedLetterResponse of(List<Letter> letters) {
        List<LetterDto> letterDtos = letters.stream()
                .map(LetterDto::from)
                .collect(Collectors.toList());
        return new ReceivedLetterResponse(letterDtos);
    }

    @Getter
    @AllArgsConstructor
    public static class LetterDto {
        private Long letterId;
        private String title;
        private String coverTypeImage;
        private String coverTypeColor;
        private LocalDateTime deliveryDate;

        public static ReceivedLetterResponse.LetterDto from(Letter letter) {
            return new ReceivedLetterResponse.LetterDto(
                    letter.getId(),
                    letter.getTitle(),
                    letter.getCoverType().getListImageUrl(),
                    letter.getCoverType().getListColor(),
                    letter.getDeliveryDate()
            );
        }
    }
}
