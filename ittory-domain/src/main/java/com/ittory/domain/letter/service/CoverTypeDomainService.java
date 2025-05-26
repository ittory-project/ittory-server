package com.ittory.domain.letter.service;

import com.ittory.domain.letter.domain.CoverType;
import com.ittory.domain.letter.dto.CoverTypeImages;
import com.ittory.domain.letter.exception.LetterException.CoverTypeNotFoundException;
import com.ittory.domain.letter.repository.CoverTypeRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CoverTypeDomainService {

    private final CoverTypeRepository coverTypeRepository;

    @Transactional
    public CoverType createCoverType(String name, CoverTypeImages images) {
        CoverType coverType = CoverType.create(name, images);
        return coverTypeRepository.save(coverType);
    }

    @Transactional(readOnly = true)
    public CoverType getCoverTypeById(Long coverTypeId) {
        return coverTypeRepository.findById(coverTypeId)
                .orElseThrow(() -> new CoverTypeNotFoundException(coverTypeId));
    }

    @Transactional(readOnly = true)
    public List<CoverType> getAllCoverType() {
        return coverTypeRepository.findAll();
    }
}
