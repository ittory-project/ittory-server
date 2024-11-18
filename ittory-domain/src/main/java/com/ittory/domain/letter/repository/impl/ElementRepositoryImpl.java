package com.ittory.domain.letter.repository.impl;

import static com.ittory.domain.letter.domain.QElement.element;
import static com.ittory.domain.letter.domain.QElementImage.elementImage;
import static com.ittory.domain.participant.domain.QParticipant.participant;

import com.ittory.domain.letter.domain.Element;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class ElementRepositoryImpl implements ElementRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Element> findAllByLetterId(Long letterId, Pageable pageable) {
        List<Element> content = jpaQueryFactory.selectFrom(element)
                .leftJoin(element.participant, participant).fetchJoin()
                .leftJoin(element.elementImage, elementImage).fetchJoin()
                .where(element.letter.id.eq(letterId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(element.sequence.asc())
                .fetch();

        //Count Query
        JPAQuery<Element> countQuery = jpaQueryFactory.selectFrom(element)
                .where(element.letter.id.eq(letterId));

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

    @Override
    public Optional<Element> findByLetterIdAndSequence(Long letterId, Integer sequence) {
        Element result = jpaQueryFactory.selectFrom(element)
                .where(element.letter.id.eq(letterId).and(element.sequence.eq(sequence)))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Element findByLetterIdAndSequenceWithImage(Long letterId, Integer sequence) {
        return jpaQueryFactory.selectFrom(element)
                .leftJoin(element.elementImage, elementImage).fetchJoin()
                .where(element.letter.id.eq(letterId).and(element.sequence.eq(sequence)))
                .fetchOne();
    }
}
