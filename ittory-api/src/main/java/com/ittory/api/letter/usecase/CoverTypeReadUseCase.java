package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.CoverTypeSearchResponse;
import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.service.CoverTypeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoverTypeReadUseCase {

    private final CoverTypeDomainService coverTypeDomainService;

    public CoverTypeSearchResponse execute(Long coverTypeId) {
        CoverType coverType = coverTypeDomainService.getCoverTypeById(coverTypeId);
        return CoverTypeSearchResponse.from(coverType);
    }
}
