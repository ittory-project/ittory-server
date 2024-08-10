package com.ittory.socket.dto;

import com.ittory.domain.letter.domain.LetterElement;
import com.ittory.domain.member.domain.Participant;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ElementResponse {

    private Long elementId;
    private Integer sort;
    private String imageUrl;
    private String content;
    private String nickname;


    public static ElementResponse of(Participant participant, LetterElement letterElement) {
        return ElementResponse.builder()
                .elementId(letterElement.getId())
                .sort(letterElement.getSort())
                .imageUrl(letterElement.getLetterImage().getUrl())
                .content(letterElement.getContent())
                .nickname(participant.getNickname())
                .build();
    }

}
