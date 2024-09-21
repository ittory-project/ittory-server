package com.ittory.domain.guestbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GuestbookColor {
    GREEN("#ECFFE1"),
    RED("#FFEFF1"),
    BLUE("#E3F8FF"),
    YELLOW("#FFF6E4");

    private final String colorHash;
}
