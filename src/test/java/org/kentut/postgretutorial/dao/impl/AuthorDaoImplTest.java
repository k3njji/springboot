package org.kentut.postgretutorial.dao.impl;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kentut.postgretutorial.TestDataUtil;
import org.kentut.postgretutorial.domain.Author;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private AuthorDaoImpl underTest;

    @Test
    public void testCreateAuthorGeneratesCorrectSql(){
        Author author = TestDataUtil.createTestAuthorA();

        underTest.create(author);

        verify(jdbcTemplate).update(
                eq("INSERT INTO authors(id, name, age) VALUES(?,?,?)"),
                eq(1L), eq("Kenzie Harsanto"), eq(22)
        );
    }

    @Test
    public void testFindOneGeneratesTheCorrectSql(){
        underTest.findOne(1L);

        verify(jdbcTemplate).query(
                eq("select * from authors where id = ? limit 1"), ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any(),
                eq(1L)
        );
    }

    @Test
    public void testThatFindManyGeneratesCorrectSql(){
        underTest.find();

        verify(jdbcTemplate).query(eq("select * from authors"), ArgumentMatchers.<AuthorDaoImpl.AuthorRowMapper>any());
    }

    @Test
    public void testThatUpdateGenerateCorrectSql(){
        Author author = TestDataUtil.createTestAuthorA();
        underTest.update(author.getId(), author);

        verify(jdbcTemplate).update(
                "update authors set id = ?, name = ?, age = ? where id = ?",
                1L, "Kenzie Harsanto", 22, 1L
        );
    }

    @Test
    public void testThatDeleteGenerateCorrectSql(){
        Author author = TestDataUtil.createTestAuthorA();
        underTest.delete(author.getId());

        verify(jdbcTemplate).update(
                "delete from authors where id = ?",
                1L
        );
    }
}
