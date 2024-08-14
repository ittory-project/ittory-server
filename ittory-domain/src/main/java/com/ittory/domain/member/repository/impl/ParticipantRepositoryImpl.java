package com.ittory.domain.member.repository.impl;


import static com.ittory.domain.letter.domain.QLetter.letter;
import static com.ittory.domain.member.domain.QMember.member;
import static com.ittory.domain.member.domain.QParticipant.participant;
import static com.ittory.domain.member.enums.ParticipantStatus.PROGRESS;

import com.ittory.domain.member.domain.Participant;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ParticipantRepositoryImpl implements ParticipantRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<Participant> findByLetterIdAndMemberId(Long letterId, Long memberId) {
        Participant fetch = jpaQueryFactory.selectFrom(participant)
                .leftJoin(participant.letter, letter).fetchJoin()
                .leftJoin(participant.member, member).fetchJoin()
                .where(participant.letter.id.eq(letterId)
                        .and(participant.member.id.eq(memberId))
                )
                .fetchOne();

        return Optional.ofNullable(fetch);
    }

    @Override
    public List<Participant> findAllCurrentByIdWithMember(List<Long> participantIds) {
        return jpaQueryFactory.selectFrom(participant)
                .leftJoin(participant.member, member).fetchJoin()
                .where(participant.participantStatus.eq(PROGRESS).and(participant.id.in(participantIds)))
                .fetch();
    }

}
