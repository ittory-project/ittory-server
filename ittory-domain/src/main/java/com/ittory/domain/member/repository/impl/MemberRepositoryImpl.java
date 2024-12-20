package com.ittory.domain.member.repository.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static com.ittory.domain.member.domain.QMember.member;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public long countSignUpByDate(LocalDate date) {
        return jpaQueryFactory.selectFrom(member)
                .where(Expressions.dateTemplate(LocalDate.class, "DATE({0})", member.createdAt)
                        .eq(date))
                .fetch()
                .size();
    }

}
