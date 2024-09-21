package com.ittory.api.guestbook.dto;

import com.ittory.domain.guestbook.domain.Guestbook;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GuestbookResponse {
    private String nickname;
    private String content;
    private String color;

    public static GuestbookResponse from(Guestbook guestbook) {
        return GuestbookResponse.builder()
                .nickname(guestbook.getNickname())
                .content(guestbook.getContent())
                .color(guestbook.getColor().getColorHash())
                .build();
    }
}
