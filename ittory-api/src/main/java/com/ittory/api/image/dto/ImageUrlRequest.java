package com.ittory.api.image.dto;

import com.ittory.infra.image.ImgExtension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ImageUrlRequest {
    private ImgExtension imgExtension;
}
