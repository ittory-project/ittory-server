package com.ittory.domain.member.repository.impl;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static com.ittory.domain.member.domain.QMemberWithdraw.memberWithdraw;

@RequiredArgsConstructor
public class MemberWithdrawRepositoryImpl implements MemberWithdrawRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;


    @Override
    public long countWithdrawByDate(LocalDate date) {
        return jpaQueryFactory.selectFrom(memberWithdraw)
                .where(Expressions.dateTemplate(LocalDate.class, "DATE({0})", memberWithdraw.createdAt)
                        .eq(date))
                .fetch()
                .size();
    }
}
