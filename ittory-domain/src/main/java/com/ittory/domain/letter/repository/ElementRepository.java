package com.ittory.domain.letter.repository;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.letter.repository.impl.ElementRepositoryCustom;
import com.ittory.domain.participant.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ElementRepository extends JpaRepository<Element, Long>, ElementRepositoryCustom {
    Integer countByParticipant(Participant participant);
}
