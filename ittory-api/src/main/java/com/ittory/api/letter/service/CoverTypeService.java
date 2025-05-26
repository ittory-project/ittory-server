package com.ittory.api.letter.service;

import com.ittory.api.letter.convertor.CoverTypeConvertor;
import com.ittory.api.letter.dto.CoverTypeCreateRequest;
import com.ittory.api.letter.dto.CoverTypeCreateResponse;
import com.ittory.api.letter.dto.CoverTypeSearchResponse;
import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.dto.CoverTypeImages;
import com.ittory.domain.letter.enums.CoverTypeStatus;
import com.ittory.domain.letter.service.CoverTypeDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CoverTypeService {

    private final CoverTypeDomainService coverTypeDomainService;
    private final CoverTypeConvertor coverTypeConvertor;

    public CoverTypeCreateResponse createCoverType(CoverTypeCreateRequest request) {
        CoverTypeImages images = coverTypeConvertor.toCoverTypeImages(request);
        CoverType coverType = coverTypeDomainService.createCoverType(request.getName(), images);
        return CoverTypeCreateResponse.from(coverType);
    }

    public CoverTypeSearchResponse getCoverType(Long coverTypeId) {
        CoverType coverType = coverTypeDomainService.getCoverTypeById(coverTypeId);
        return CoverTypeSearchResponse.from(coverType);
    }

    public List<CoverTypeSearchResponse> getAllActiveCoverType() {
        return coverTypeDomainService.getAllCoverType().stream()
                .filter(CoverType::isActive)
                .map(CoverTypeSearchResponse::from)
                .toList();
    }

}
