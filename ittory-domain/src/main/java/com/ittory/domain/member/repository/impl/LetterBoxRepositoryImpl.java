package com.ittory.domain.member.repository.impl;

import static com.ittory.domain.member.domain.QLetterBox.letterBox;
import static com.ittory.domain.member.enums.LetterBoxType.RECEIVE;

import com.ittory.domain.member.domain.LetterBox;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

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

}
