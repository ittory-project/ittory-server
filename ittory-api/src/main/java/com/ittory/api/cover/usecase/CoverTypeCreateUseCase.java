package com.ittory.api.cover.usecase;

import com.ittory.api.cover.dto.CoverTypeCreateRequest;
import com.ittory.api.cover.dto.CoverTypeCreateResponse;
import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.service.CoverTypeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoverTypeCreateUseCase {

    private final CoverTypeDomainService coverTypeDomainService;

    public CoverTypeCreateResponse execute(CoverTypeCreateRequest request) {
        CoverType coverType = coverTypeDomainService.createCoverType(request.getName(), request.getUrl());
        return CoverTypeCreateResponse.from(coverType);
    }
}
