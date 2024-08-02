package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.exception.CoverTypeException;
import com.ittory.domain.letter.repository.CoverTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoverTypeDomainService {

    private final CoverTypeRepository coverTypeRepository;

    public CoverType createCoverType(String name, String url) {
        CoverType coverType = CoverType.create(name, url);
        return coverTypeRepository.save(coverType);
    }

    public CoverType getCoverTypeById(Long coverTypeId) {
        return coverTypeRepository.findById(coverTypeId)
                .orElseThrow(() -> new CoverTypeException.CoverTypeNotFoundException(coverTypeId));
    }
}
