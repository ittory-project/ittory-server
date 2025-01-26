package com.ittory.domain.letter.repository.impl;

import com.ittory.domain.letter.domain.Element;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
public class LetterElementRepositoryImpl implements LetterElementRepositoryCustom {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void saveAllInBatch(List<Element> elements) {
        String sqlQuery = "INSERT INTO element (letter_id, element_image_id, sequence, created_at, updated_at)" +
                " VALUES (?, ?, ?, NOW(), NOW())";
        jdbcTemplate.batchUpdate(sqlQuery, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Element element = elements.get(i);
                ps.setLong(1, element.getLetter().getId());
                ps.setLong(2, element.getElementImage().getId());
                ps.setInt(3, element.getSequence());
            }

            @Override
            public int getBatchSize() {
                return elements.size();
            }
        });

    }
}
