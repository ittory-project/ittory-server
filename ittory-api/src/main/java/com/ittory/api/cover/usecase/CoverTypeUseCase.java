package com.ittory.api.cover.usecase;

import com.ittory.api.cover.dto.CoverTypeCreateRequest;
import com.ittory.api.cover.dto.CoverTypeCreateResponse;
import com.ittory.api.cover.dto.CoverTypeSearchResponse;
import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.repository.CoverTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoverTypeUseCase {

    private final CoverTypeRepository coverTypeRepository;

    public CoverTypeCreateResponse createCoverType(CoverTypeCreateRequest request) {
        CoverType coverType = CoverType.create(request.getName(), request.getUrl());
        coverType = coverTypeRepository.save(coverType);
        return CoverTypeCreateResponse.from(coverType);
    }

    public CoverTypeSearchResponse getCoverTypeById(Long coverTypeId) {
        CoverType coverType = coverTypeRepository.findById(coverTypeId).orElseThrow(IllegalArgumentException::new);
        return CoverTypeSearchResponse.from(coverType);
    }
}
