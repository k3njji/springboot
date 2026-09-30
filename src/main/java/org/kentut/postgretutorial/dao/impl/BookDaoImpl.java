package org.kentut.postgretutorial.dao.impl;

import org.kentut.postgretutorial.dao.BookDao;
import org.kentut.postgretutorial.domain.Book;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component
public class BookDaoImpl implements BookDao {
    private final JdbcTemplate jdbcTemplate;

    public BookDaoImpl(final JdbcTemplate jdbcTemplate, JdbcTemplate jdbcTemplate1){
//        this.jdbcTemplate = jdbcTemplate1;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void create(Book book) {
        jdbcTemplate.update(
                "insert into books(isbn, title, author_id) values(?,?,?)",
                book.getIsbn(), book.getTitle(), book.getAuthorId()
        );

    }

    @Override
    public Optional<Book> findOne(String s) {

        List<Book> results = jdbcTemplate.query("select * from books where isbn = ? limit 1",
                new AutoRowMapper(), s);

        return results.stream().findFirst();
    }

    @Override
    public List<Book> find() {
        return jdbcTemplate.query("select * from books", new AutoRowMapper());
    }

    @Override
    public void update(String isbn, Book book) {
    jdbcTemplate.update("update books set isbn = ?, title = ?, author_id = ? where isbn = ?",
            book.getIsbn(), book.getTitle(), book.getAuthorId(), isbn
    );
    }
//    @Test
//    public void testThatUpdateGenerateCorrectSql(){
////        Author author = TestDataUtil.createTestAuthorA()
//
//        Book book = TestDataUtil.createTestBookA();
//
//        underTest.create(book);
//
//        verify(jdbcTemplate).update(
//                eq("insert into books(isbn, title, author_id) values(?,?,?)"),
//                eq("087-776-161-1712"), eq("Happily Ever After"), eq(1L)
//        );
//        underTest.update("087-776-161-1712", book);
//
//        verify(jdbcTemplate).update(
//                "update books set isbn = ?, title = ?, author_id = ? where isbn = ?",
//                "087-776-161-1712", "Happily Ever After", 1L, "087-776-161-1712"
//        );
//    }

    public static class AutoRowMapper implements RowMapper<Book>{
        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return Book.builder()
                    .isbn(rs.getString("isbn"))
                    .title(rs.getString("title"))
                    .authorId(rs.getLong("author_id"))
                    .build();
        }
    }
}
