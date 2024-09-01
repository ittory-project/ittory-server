package com.ittory.api.member.dto;

import com.ittory.domain.letter.domain.Letter;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ParticipationResponse {
    private List<LetterDto> letters;

    public static ParticipationResponse of(List<Letter> letters) {
        List<LetterDto> letterDtos = letters.stream()
                .map(LetterDto::from)
                .collect(Collectors.toList());
        return new ParticipationResponse(letterDtos);
    }

    @Getter
    @AllArgsConstructor
    public static class LetterDto {
        private Long letterId;
        private String title;

        public static LetterDto from(Letter letter) {
            return new LetterDto(
                    letter.getId(),
                    letter.getTitle()
            );
        }
    }
}