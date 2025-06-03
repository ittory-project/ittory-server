package com.ittory.domain.participant.repository.impl;


import com.ittory.domain.participant.domain.Participant;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.ittory.domain.letter.domain.QLetter.letter;
import static com.ittory.domain.member.domain.QMember.member;
import static com.ittory.domain.participant.domain.QParticipant.participant;
import static com.ittory.domain.participant.enums.ParticipantStatus.*;

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
    public List<Participant> findCurrentParticipantsByLetterIdOrdered(Long letterId,
                                                                      Boolean isAscending) {
        return jpaQueryFactory.selectFrom(participant)
                .leftJoin(participant.member, member).fetchJoin()
                .where(participant.letter.id.in(letterId)
                        .and(participant.participantStatus.eq(ENTER)
                                .or(participant.participantStatus.eq(PROGRESS))
                                .or(participant.participantStatus.eq(COMPLETED))
                        )
                )
                .orderBy(getOrderParticipant(isAscending))
                .fetch();
    }

    private static OrderSpecifier<?> getOrderParticipant(Boolean isAscending) {
        if (isAscending == null) {
            return participant.updatedAt.asc();
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

    @Override
    public List<Participant> findAllParticipantsWithMember(Long letterId) {
        return jpaQueryFactory.selectFrom(participant)
                .leftJoin(participant.member, member).fetchJoin()
                .where(participant.letter.id.eq(letterId).and(participant.participantStatus.ne(EXITED)))
                .orderBy(participant.updatedAt.desc())
                .fetch();
    }

    @Override
    public Participant findEnterByNickname(Long letterId, String nickname) {
        return jpaQueryFactory.selectFrom(participant)
                .where(participant.letter.id.eq(letterId)
                        .and(participant.nickname.eq(nickname))
                        .and(participant.participantStatus.eq(ENTER))
                )
                .fetchOne();
    }

    @Override
    public Integer countProgressByLetterId(Long letterId) {
        return jpaQueryFactory.selectFrom(participant)
                .where(participant.letter.id.eq(letterId)
                        .and(participant.participantStatus.eq(ENTER))
                )
                .fetch().size();
    }

    @Override
    public Participant findManagerByLetterId(Long letterId) {
        return jpaQueryFactory.selectFrom(participant)
                .leftJoin(participant.letter, letter).fetchJoin()
                .where(letter.id.eq(letterId))
                .orderBy(participant.updatedAt.asc())
                .limit(1)
                .fetchOne();
    }

    @Override
    public void updateAllStatusToStart(Long letterId) {
        jpaQueryFactory.update(participant)
                .where(participant.letter.id.eq(letterId).and(participant.participantStatus.eq(ENTER)))
                .set(participant.participantStatus, PROGRESS)
                .execute();
    }

    @Override
    public void updateAllStatusToEnd(Long letterId) {
        jpaQueryFactory.update(participant)
                .where(participant.letter.id.eq(letterId).and(participant.participantStatus.eq(PROGRESS)))
                .set(participant.participantStatus, COMPLETED)
                .execute();
    }

    @Override
    public void updateAllStatusToDelete(Long letterId) {
        jpaQueryFactory.update(participant)
                .where(participant.letter.id.eq(letterId).and(participant.participantStatus.eq(ENTER)))
                .set(participant.participantStatus, EXITED)
                .execute();
    }

    @Override
    public Integer countEnterParticipantByLetterId(Long letterId) {
        return jpaQueryFactory.selectFrom(participant)
                .where(participant.letter.id.eq(letterId)
                        .and(participant.participantStatus.eq(ENTER).or(participant.participantStatus.eq(GHOST)))
                )
                .fetch().size();
    }

    @Override
    public Optional<Participant> findEnterParticipantByLetterIdAndMemberId(Long letterId, Long memberId) {
        Participant fetch = jpaQueryFactory.selectFrom(participant)
                .leftJoin(participant.letter, letter).fetchJoin()
                .leftJoin(participant.member, member).fetchJoin()
                .where(participant.letter.id.eq(letterId)
                        .and(participant.member.id.eq(memberId))
                        .and(participant.participantStatus.eq(ENTER).or(participant.participantStatus.eq(GHOST))
                        )
                )
                .fetchOne();

        return Optional.ofNullable(fetch);
    }

    @Override
    public Optional<Participant> findByLetterIdAndSequence(Long letterId, Integer sequence) {
        Participant content = jpaQueryFactory.selectFrom(participant)
                .where(participant.letter.id.eq(letterId)
                        .and(participant.sequence.eq(sequence))
                )
                .limit(1)
                .fetchOne();

        return Optional.ofNullable(content);
    }

    @Override
    public List<Participant> findAllProgressParticipantsWithMember(Long letterId) {
        return jpaQueryFactory.selectFrom(participant)
                .leftJoin(participant.member, member).fetchJoin()
                .where(participant.letter.id.eq(letterId).and(participant.participantStatus.eq(PROGRESS)))
                .fetch();
    }
}
