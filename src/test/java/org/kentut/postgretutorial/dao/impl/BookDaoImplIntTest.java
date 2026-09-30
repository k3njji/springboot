package org.kentut.postgretutorial.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kentut.postgretutorial.TestDataUtil;
import org.kentut.postgretutorial.dao.AuthorDao;
import org.kentut.postgretutorial.domain.Author;
import org.kentut.postgretutorial.domain.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookDaoImplIntTest {

    private AuthorDao authorDao;
    private BookDaoImpl underTest;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDaoImplIntTest(BookDaoImpl undertest, AuthorDao authorDao){
        this.underTest = undertest;
        this.authorDao = authorDao;
    }

    @Test
    public void testThatAuthorCanBeCreateAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        authorDao.create(author);

        Book book = TestDataUtil.createTestBookA();

        book.setAuthorId(author.getId());

        underTest.create(book);

        Optional<Book> result = underTest.findOne("asd");
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(book);
    }

    @Test
    public void testThatMultipleBookCanBeCreatedAndRecalled(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();

        Book bookA = TestDataUtil.createTestBookA();
        bookA.setAuthorId(authorA.getId());


        Book bookB = TestDataUtil.createTestBookB();
        bookB.setAuthorId(authorB.getId());

        Book bookC = TestDataUtil.createTestBookC();
        bookB.setAuthorId(authorC.getId());


        authorDao.create(authorA);
        authorDao.create(authorB);
        authorDao.create(authorC);

        underTest.create(bookA);
        underTest.create(bookB);
        underTest.create(bookC);

        List<Book> result = underTest.find();
        assertThat(result).hasSize(3).containsExactly(bookA, bookB, bookC);
    }

    @Test
    public void testThatBookUpdateGenerateCorrectSql(){
        Author author = TestDataUtil.createTestAuthorA();
        Book book = TestDataUtil.createTestBookA();
        book.setAuthorId(author.getId());

        authorDao.create(author);
        underTest.create(book);

        book.setTitle("Bro LoL");
        underTest.update(book.getIsbn(), book);

        Optional<Book> result = underTest.findOne(book.getIsbn());

        System.out.println("Book updated = " + result.get());
    }
}
