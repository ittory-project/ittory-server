package com.ittory.api.letter.usecase;

import com.ittory.api.letter.dto.CoverTypeSearchResponse;
import com.ittory.domain.letter.service.CoverTypeDomainService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoverTypeAllReadUseCase {

    private final CoverTypeDomainService coverTypeDomainService;

    public List<CoverTypeSearchResponse> execute() {
        return coverTypeDomainService.findAllCoverType().stream()
                .map(CoverTypeSearchResponse::from)
                .toList();
    }

}
