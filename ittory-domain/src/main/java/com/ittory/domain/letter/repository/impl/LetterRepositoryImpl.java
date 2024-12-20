package com.ittory.domain.letter.repository.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static com.ittory.domain.letter.domain.QLetter.letter;
import static com.ittory.domain.letter.enums.LetterStatus.COMPLETED;

@RequiredArgsConstructor
public class LetterRepositoryImpl implements LetterRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long countByCreatedAt(LocalDate date) {
        return jpaQueryFactory.selectFrom(letter)
                .where(Expressions.dateTemplate(LocalDate.class, "DATE({0})", letter.createdAt)
                        .eq(date).and(letter.letterStatus.eq(COMPLETED)))
                .fetch()
                .size();
    }
}
