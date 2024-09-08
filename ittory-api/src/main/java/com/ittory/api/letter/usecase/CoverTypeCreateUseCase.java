package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.CoverTypeCreateRequest;
import com.ittory.api.letter.dto.CoverTypeCreateResponse;
import com.ittory.api.letter.mapper.CoverTypeMapper;
import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.dto.CoverTypeImages;
import com.ittory.domain.letter.service.CoverTypeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoverTypeCreateUseCase {

    private final CoverTypeDomainService coverTypeDomainService;

    private final CoverTypeMapper coverTypeMapper;

    public CoverTypeCreateResponse execute(CoverTypeCreateRequest request) {
        CoverTypeImages images = coverTypeMapper.toCoverTypeImages(request);
        CoverType coverType = coverTypeDomainService.createCoverType(request.getName(), images);
        return CoverTypeCreateResponse.from(coverType);
    }
}
