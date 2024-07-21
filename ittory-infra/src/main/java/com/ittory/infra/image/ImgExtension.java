package com.ittory.infra.image;

import lombok.Getter;

@Getter
public enum ImgExtension {
    JPG("jpeg"),
    JPEG("jpeg"),
    PNG("png");

    ImgExtension(String uploadExtension) {
        this.uploadExtension = uploadExtension;
    }

    private final String uploadExtension;
}
