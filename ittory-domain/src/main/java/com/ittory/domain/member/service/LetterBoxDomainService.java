package com.ittory.domain.member.service;

import com.ittory.domain.member.repository.LetterBoxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LetterBoxDomainService {

    private final LetterBoxRepository letterBoxRepository;

    @Transactional(readOnly = true)
    public Boolean checkStorageStatus(Long letterId) {
        return letterBoxRepository.existsInLetterBox(letterId);
    }

}
