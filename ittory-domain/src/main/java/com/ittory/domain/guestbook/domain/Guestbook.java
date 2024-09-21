package com.ittory.domain.guestbook.domain;

import com.ittory.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "guestbook")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Guestbook extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guestbook_id")
    private Long id;

    private String nickname;

    private String content;

    @Enumerated(EnumType.STRING)
    private GuestbookColor color;

    public static Guestbook create(String nickname, String content, GuestbookColor color) {
        return Guestbook.builder()
                .nickname(nickname)
                .content(content)
                .color(color)
                .build();
    }

}
