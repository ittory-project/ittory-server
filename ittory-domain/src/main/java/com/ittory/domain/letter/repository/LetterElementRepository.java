package com.ittory.domain.letter.repository;

import com.ittory.domain.letter.domain.LetterElement;
import com.ittory.domain.member.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LetterElementRepository extends JpaRepository<LetterElement, Long> {
    Integer countByParticipant(Participant participant);
}
