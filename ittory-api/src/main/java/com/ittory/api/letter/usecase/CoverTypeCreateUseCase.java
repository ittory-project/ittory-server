package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.CoverTypeCreateRequest;
import com.ittory.api.letter.dto.CoverTypeCreateResponse;
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
