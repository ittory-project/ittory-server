package com.ittory.domain.participant.repository.impl;


import static com.ittory.domain.letter.domain.QLetter.letter;
import static com.ittory.domain.member.domain.QMember.member;
import static com.ittory.domain.participant.domain.QParticipant.participant;
import static com.ittory.domain.participant.enums.ParticipantStatus.PROGRESS;

import com.ittory.domain.participant.domain.Participant;
import com.querydsl.core.types.OrderSpecifier;
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
    public List<Participant> findCurrentParticipantsByLetterIdOrdered(Long letterId, Boolean isAscending) {
        return jpaQueryFactory.selectFrom(participant)
                .leftJoin(participant.member, member).fetchJoin()
                .where(participant.participantStatus.eq(PROGRESS).and(participant.letter.id.in(letterId)))
                .orderBy(getOrderParticipant(isAscending))
                .fetch();
    }

    private static OrderSpecifier<?> getOrderParticipant(Boolean isAscending) {
        if (isAscending == null) {
            return participant.createdAt.asc();
        }
        return isAscending ? participant.sequence.asc() : participant.sequence.desc();
    }

    @Override
    public List<Participant> findAllOrderNext(Long letterId, Integer sequence) {
        return jpaQueryFactory.selectFrom(participant)
                .where(participant.letter.id.eq(letterId)
                        .and(participant.sequence.gt(sequence))
                        .and(participant.participantStatus.eq(PROGRESS))
                ).fetch();
    }

}