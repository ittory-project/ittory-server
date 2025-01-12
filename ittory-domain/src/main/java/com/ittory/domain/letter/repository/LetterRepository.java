package com.ittory.domain.letter.repository;

import com.ittory.domain.letter.domain.Letter;
import com.ittory.domain.letter.repository.impl.LetterRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LetterRepository extends JpaRepository<Letter, Long>, LetterRepositoryCustom {
    List<Letter> findByReceiverIdOrderByDeliveryDateDesc(Long receiverId);

    @Query("SELECT l FROM letter l " +
            "LEFT JOIN FETCH l.coverType " +
            "LEFT JOIN FETCH l.font " +
            "WHERE l.id = :letterId")
    Letter findByIdWithAssociations(@Param("letterId") Long letterId);
}