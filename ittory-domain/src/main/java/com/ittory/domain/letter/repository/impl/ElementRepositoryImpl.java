package com.ittory.domain.letter.repository.impl;

import com.ittory.domain.letter.domain.Element;
import com.ittory.domain.participant.domain.Participant;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;
import java.util.Optional;

import static com.ittory.domain.letter.domain.QElement.element;
import static com.ittory.domain.letter.domain.QElementImage.elementImage;
import static com.ittory.domain.letter.domain.QLetter.letter;
import static com.ittory.domain.member.domain.QMember.member;
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
                .leftJoin(element.participant, participant).fetchJoin()
                .leftJoin(participant.member, member).fetchJoin()
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
    public Integer countNotNullByParticipant(Participant participant) {
        return jpaQueryFactory.selectFrom(element)
                .where(element.participant.eq(participant)
                        .and(element.content.isNotNull())
                ).fetch().size();
    }
}
