package com.ittory.domain.letter.repository;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.repository.impl.ElementRepositoryCustom;
import com.ittory.domain.participant.domain.Participant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ElementRepository extends JpaRepository<Element, Long>, ElementRepositoryCustom {
    Integer countByParticipant(Participant participant);

    @Query("SELECT e FROM element e " +
            "LEFT JOIN FETCH e.participant " +
            "WHERE e.letter.id = :letterId")
    List<Element> findAllByLetterIdWithParticipant(@Param("letterId") Long letterId);

    @Query("SELECT e FROM element e " +
            "LEFT JOIN FETCH e.participant " +
            "WHERE e.id = :elementId")
    Optional<Element> findByIdWithParticipant(@Param("elementId") Long elementId);

    @Query("SELECT e FROM element e " +
            "LEFT JOIN FETCH e.elementImage " +
            "WHERE e.letter.id = :letterId AND e.sequence = :sequence")
    Element findByLetterIdAndSequenceWithImage(@Param("letterId") Long letterId, @Param("sequence") Integer sequence);


    @Query("SELECT e FROM element e " +
            "LEFT JOIN FETCH e.participant " +
            "WHERE e.letter.id = :letterId")
    Page<Element> findPageByLetterIdWithParticipant(@Param("letterId") Long letterId, Pageable pageable);

    @Modifying
    @Query("DELETE FROM element e WHERE e.letter.id = :letterId")
    void deleteAllByLetterId(@Param("letterId") Long letterId);
}
