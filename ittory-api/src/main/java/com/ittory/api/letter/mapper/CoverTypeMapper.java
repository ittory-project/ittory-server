package com.ittory.api.letter.mapper;

import com.ittory.api.letter.dto.CoverTypeCreateRequest;
import com.ittory.domain.letter.dto.CoverTypeImages;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CoverTypeMapper {

    public CoverTypeImages toCoverTypeImages(CoverTypeCreateRequest request) {
        return CoverTypeImages.of(request.getListImageUrl(), request.getSelectImageUrl(), request.getEditImageUrl(),
                request.getConfirmImageUrl(), request.getOutputBackgroundImageUrl(), request.getLoadingBackgroundImageUrl());
    }

}
