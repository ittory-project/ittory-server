package com.ittory.socket.dto;

import com.ittory.domain.letter.domain.LetterElement;
import com.ittory.domain.member.domain.Member;
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
    private String imageUrl;
    private String content;
    private String name;


    public static ElementResponse of(Member member, LetterElement letterElement) {
        return ElementResponse.builder()
                .elementId(letterElement.getId())
                .imageUrl(letterElement.getLetterImage().getUrl())
                .content(letterElement.getContent())
                .name(member.getName())
                .build();
    }

}
