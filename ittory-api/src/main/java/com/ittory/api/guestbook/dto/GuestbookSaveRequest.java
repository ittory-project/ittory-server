package com.ittory.api.guestbook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GuestbookSaveRequest {
    private String nickname;
    private String content;
}
