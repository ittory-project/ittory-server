package com.ittory.domain.member.repository.impl;

import com.ittory.domain.member.domain.LetterBox;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.Lock;

public interface LetterBoxRepositoryCustom {
    Boolean existsInLetterBox(Long letterId);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<LetterBox> findReceivedByLetterIdWithLock(Long letterId);
}
