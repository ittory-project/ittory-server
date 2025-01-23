package com.ittory.domain.letter.repository.impl;

import com.ittory.domain.letter.domain.Element;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.ittory.domain.letter.domain.QElement.element;
import static com.ittory.domain.letter.domain.QElementImage.elementImage;
import static com.ittory.domain.letter.domain.QLetter.letter;
import static com.ittory.domain.participant.domain.QParticipant.participant;

@RequiredArgsConstructor
public class ElementRepositoryImpl implements ElementRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Element> findPageByLetterId(Long letterId, Pageable pageable) {
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

    @Override
    public List<Element> findAllByLetterId(Long letterId) {
        return jpaQueryFactory.selectFrom(element)
                .leftJoin(element.letter, letter).fetchJoin()
                .leftJoin(element.participant, participant).fetchJoin()
                .leftJoin(element.elementImage, elementImage).fetchJoin()
                .where(element.letter.id.eq(letterId))
                .fetch();
    }



    @Override
    public List<Element> findAllByLetterIdWithParticipant(Long letterId) {
        return jpaQueryFactory.selectFrom(element)
                .leftJoin(element.participant, participant).fetchJoin()
                .where(element.letter.id.eq(letterId))
                .fetch();
    }

    @Override
    public Optional<Element> findByIdWithParticipant(Long elementId) {
        Element result = jpaQueryFactory.selectFrom(element)
                .leftJoin(element.participant, participant).fetchJoin()
                .where(element.id.eq(elementId))
                .fetchOne();
        return Optional.ofNullable(result);
    }

    @Override
    public Page<Element> findPageByLetterIdWithParticipant(Long letterId, Pageable pageable) {
        List<Element> content = jpaQueryFactory.selectFrom(element)
                .leftJoin(element.participant, participant).fetchJoin()
                .leftJoin(element.elementImage, elementImage).fetchJoin()
                .where(element.letter.id.eq(letterId))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(element.sequence.asc())
                .fetch();

        // Count Query
        long total = jpaQueryFactory.select(element.count())
                .from(element)
                .where(element.letter.id.eq(letterId))
                .fetchOne();

        return PageableExecutionUtils.getPage(content, pageable, () -> total);
    }

    @Override
    @Transactional
    public void deleteAllByLetterId(Long letterId) {
        jpaQueryFactory.delete(element)
                .where(element.letter.id.eq(letterId))
                .execute();
    }
}
