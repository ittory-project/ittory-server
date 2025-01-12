package com.ittory.domain.member.repository;

import com.ittory.domain.member.domain.LetterBox;
import com.ittory.domain.member.enums.LetterBoxType;
import com.ittory.domain.member.repository.impl.LetterBoxRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LetterBoxRepository extends JpaRepository<LetterBox, Long>, LetterBoxRepositoryCustom {
    void deleteByMemberIdAndLetterId(Long memberId, Long letterId);

    //    List<LetterBox> findAllByMemberIdAndLetterBoxType(Long memberId, LetterBoxType letterBoxType);

    @Query("SELECT lb FROM letter_box lb " +
            "LEFT JOIN FETCH lb.letter " +
            "WHERE lb.member.id = :memberId AND lb.letterBoxType = :letterBoxType")
    List<LetterBox> findAllByMemberIdAndLetterBoxType(@Param("memberId") Long memberId,
                                                      @Param("letterBoxType") LetterBoxType letterBoxType);





}
