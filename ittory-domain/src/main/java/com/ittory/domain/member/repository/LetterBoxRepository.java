package com.ittory.domain.member.repository;

import com.ittory.domain.member.domain.LetterBox;
import com.ittory.domain.member.repository.impl.LetterBoxRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterBoxRepository extends JpaRepository<LetterBox, Long>, LetterBoxRepositoryCustom {
    void deleteByMemberIdAndLetterId(Long memberId, Long letterId);
}
