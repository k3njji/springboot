package org.kentut.postgretutorial.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kentut.postgretutorial.TestDataUtil;
import org.kentut.postgretutorial.domain.Author;
import org.kentut.postgretutorial.domain.Book;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplTest {
    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private BookDaoImpl underTest;

    @Test
    public void testCreateBookGeneratesCorrectSql(){
        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("insert into books(isbn, title, author_id) values(?,?,?)"),
                eq("087-776-161-1712"), eq("Happily Ever After"), eq(1L)
        );
    }

    @Test
    public void testFindOneBookGeneratesCorrectSql(){
        underTest.findOne("087-776-161-1712");

        verify(jdbcTemplate).query(
                eq("select * from books where isbn = ? limit 1"), ArgumentMatchers.<BookDaoImpl.AutoRowMapper>any(),
                eq("087-776-161-1712"));
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql(){
        underTest.find();
        verify(jdbcTemplate).query(eq("select * from books"), ArgumentMatchers.<BookDaoImpl.AutoRowMapper>any());
    }

    @Test
    public void testThatUpdateGenerateCorrectSql(){
//        Author author = TestDataUtil.createTestAuthorA()

        Book book = TestDataUtil.createTestBookA();

        underTest.create(book);

        verify(jdbcTemplate).update(
                eq("insert into books(isbn, title, author_id) values(?,?,?)"),
                eq("087-776-161-1712"), eq("Happily Ever After"), eq(1L)
        );
        underTest.update("087-776-161-1712", book);

        verify(jdbcTemplate).update(
                "update books set isbn = ?, title = ?, author_id = ? where isbn = ?",
                "087-776-161-1712", "Happily Ever After", 1L, "087-776-161-1712"
        );
    }

//    public static Book createTestBookA() {
//        return Book.builder()
//                .isbn("087-776-161-1712")
//                .title("Happily Ever After")
//                .authorId(1L)
//                .build();
//    }

}
