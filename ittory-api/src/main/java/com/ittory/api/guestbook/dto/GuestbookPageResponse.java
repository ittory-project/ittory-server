package com.ittory.api.guestbook.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GuestbookPageResponse {

    private Boolean hasNext;
    private Integer pageNumber;
    private List<GuestbookResponse> content;

    public static GuestbookPageResponse of(Boolean hasNext, Integer pageNumber, List<GuestbookResponse> content) {
        return GuestbookPageResponse.builder()
                .hasNext(hasNext)
                .pageNumber(pageNumber)
                .content(content)
                .build();
    }

}
