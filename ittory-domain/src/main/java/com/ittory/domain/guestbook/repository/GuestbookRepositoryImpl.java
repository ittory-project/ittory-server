package com.ittory.domain.guestbook.repository;

import com.ittory.domain.guestbook.domain.Guestbook;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.util.List;

import static com.ittory.domain.guestbook.domain.QGuestbook.guestbook;

@RequiredArgsConstructor
public class GuestbookRepositoryImpl implements GuestbookRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Guestbook> findGuestbookPage(Pageable pageable) {
        List<Guestbook> content = jpaQueryFactory.selectFrom(guestbook)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(guestbook.createdAt.desc())
                .fetch();

        // Count Query
        JPAQuery<Guestbook> countQuery = jpaQueryFactory.selectFrom(guestbook);

        return PageableExecutionUtils.getPage(content, pageable, () -> countQuery.fetch().size());
    }

}
