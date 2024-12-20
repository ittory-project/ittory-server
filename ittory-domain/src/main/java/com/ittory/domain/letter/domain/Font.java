package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "font")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Font extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "font_id")
    private Long id;

    private String name;

    @Column(name = "font_value")
    private String value;

    public static Font create(String name, String value) {
        return Font.builder()
                .name(name)
                .value(value)
                .build();
    }

}
