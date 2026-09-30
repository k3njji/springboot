package org.kentut.postgretutorial.dao.impl;

import org.kentut.postgretutorial.dao.AuthorDao;
import org.kentut.postgretutorial.domain.Author;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static javax.management.Query.eq;
@Component
public class AuthorDaoImpl implements AuthorDao {
    private final JdbcTemplate jdbcTemplate;

    public AuthorDaoImpl(final JdbcTemplate jdbcTemplate, JdbcTemplate jdbcTemplate1){
//        this.jdbcTemplate = jdbcTemplate1;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Author author){
        jdbcTemplate.update(
                "INSERT INTO authors(id, name, age) VALUES(?,?,?)",
                        author.getId(), author.getName(), author.getAge()
        );
    }

    @Override
    public Optional<Author> findOne(long l) {
        List<Author> results = jdbcTemplate.query(
        "select * from authors where id = ? limit 1",
                new AuthorRowMapper(), l);

        return results.stream().findFirst();
    }

    @Override
    public List<Author> find() {
        return jdbcTemplate.query("select * from authors", new AuthorRowMapper());

    }

    @Override
    public void update(long id, Author author) {
        jdbcTemplate.update(
                "update authors set id = ?, name = ?, age = ? where id = ?",
                author.getId(), author.getName(), author.getAge(), id
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(
                "delete from authors where id = ?",
                id
        );
    }

    public static class AuthorRowMapper implements RowMapper<Author>{

        @Override
        public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Author.builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .age(rs.getInt("age"))
                    .build();
        }
    }
}
