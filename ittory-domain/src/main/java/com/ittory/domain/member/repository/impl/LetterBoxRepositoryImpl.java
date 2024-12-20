package com.ittory.domain.member.repository.impl;

import com.ittory.domain.member.domain.LetterBox;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.ittory.domain.member.domain.QLetterBox.letterBox;
import static com.ittory.domain.member.enums.LetterBoxType.PARTICIPATION;
import static com.ittory.domain.member.enums.LetterBoxType.RECEIVE;

@RequiredArgsConstructor
public class LetterBoxRepositoryImpl implements LetterBoxRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Boolean existsInLetterBox(Long letterId) {
        List<LetterBox> result = jpaQueryFactory.selectFrom(letterBox)
                .where(letterBox.letter.id.eq(letterId)
                        .and(letterBox.letterBoxType.eq(RECEIVE))
                ).fetch();
        return result.size() > 0;
    }

    @Override
    public Optional<LetterBox> findReceivedByLetterIdWithLock(Long letterId) {
        LetterBox result = jpaQueryFactory.selectFrom(letterBox)
                .where(letterBox.letter.id.eq(letterId)
                        .and(letterBox.letterBoxType.eq(RECEIVE))
                )
//                .setLockMode(LockModeType.PESSIMISTIC_READ)
                .fetchFirst();
        return Optional.ofNullable(result);
    }

    @Override
    public Integer countParticipationLetterByMemberId(Long memberId) {
        return jpaQueryFactory.selectFrom(letterBox)
                .where(letterBox.member.id.eq(memberId).and(letterBox.letterBoxType.eq(PARTICIPATION)))
                .fetch().size();
    }

    @Override
    public Integer countReceiveLetterByMemberId(Long memberId) {
        return jpaQueryFactory.selectFrom(letterBox)
                .where(letterBox.member.id.eq(memberId).and(letterBox.letterBoxType.eq(RECEIVE)))
                .fetch().size();
    }

    @Override
    public long countReceivedLetterByCreatedAt(LocalDate date) {
        return jpaQueryFactory.selectFrom(letterBox)
                .where(Expressions.dateTemplate(LocalDate.class, "DATE({0})", letterBox.createdAt)
                        .eq(date).and(letterBox.letterBoxType.eq(RECEIVE)))
                .fetch()
                .size();
    }

}
