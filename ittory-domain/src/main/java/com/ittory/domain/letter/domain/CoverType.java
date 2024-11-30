package com.ittory.domain.letter.domain;

import com.ittory.domain.common.BaseEntity;
import com.ittory.domain.letter.dto.CoverTypeImages;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "cover_type")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CoverType extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cover_type_id")
    private Long id;

    private String name;

    @Column(name = "list_image_url")
    private String listImageUrl;

    @Column(name = "select_image_url")
    private String selectImageUrl;

    @Column(name = "edit_image_url")
    private String editImageUrl;

    @Column(name = "confirm_image_url")
    private String confirmImageUrl;

    @Column(name = "output_background_image_url")
    private String outputBackgroundImageUrl;

    @Column(name = "loading_background_image_url")
    private String loadingBackgroundImageUrl;

    @Column(name = "output_board_image_url")
    private String outputBoardImageUrl;

    @Column(name = "list_color")
    private String listColor;


    public static CoverType create(String name, CoverTypeImages request) {
        return CoverType.builder()
                .name(name)
                .listImageUrl(request.getListImageUrl())
                .selectImageUrl(request.getSelectImageUrl())
                .editImageUrl(request.getEditImageUrl())
                .confirmImageUrl(request.getConfirmImageUrl())
                .outputBackgroundImageUrl(request.getOutputBackgroundImageUrl())
                .loadingBackgroundImageUrl(request.getLoadingBackgroundImageUrl())
                .build();
    }
}
