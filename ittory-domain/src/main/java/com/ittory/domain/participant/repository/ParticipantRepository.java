package com.ittory.domain.participant.repository;

import com.ittory.domain.participant.domain.Participant;
import com.ittory.domain.participant.repository.impl.ParticipantRepositoryCustom;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long>, ParticipantRepositoryCustom {
    List<Participant> findByMemberId(Long memberId);

}
