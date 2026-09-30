package org.kentut.postgretutorial.dao.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.kentut.postgretutorial.TestDataUtil;
import org.kentut.postgretutorial.domain.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AuthorDaoImplIntTest {

    private AuthorDaoImpl underTest;

    @Autowired
    public AuthorDaoImplIntTest(AuthorDaoImpl underTest){
        this.underTest = underTest;
    }

    @Test
    public void testThatAuthorCanBeCreateAndRecalled(){
        Author author = TestDataUtil.createTestAuthorA();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(author);
    }

    @Test
    public void testThatMultipleAuthorsCanBeCreatedAndRecalled(){
        Author authorA = TestDataUtil.createTestAuthorA();
        Author authorB = TestDataUtil.createTestAuthorB();
        Author authorC = TestDataUtil.createTestAuthorC();


        underTest.create(authorA);
        underTest.create(authorB);
        underTest.create(authorC);

        List<Author> result = underTest.find();
        assertThat(result).hasSize(3).containsExactly(authorA, authorB, authorC);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        Author authorA = TestDataUtil.createTestAuthorA();
        underTest.create(authorA);

        authorA.setName("UPDATED");
        underTest.update(authorA.getId(), authorA);

        Optional<Author> result = underTest.findOne(authorA.getId());

        assertThat(result).isPresent();
        System.out.println("Updated author: " + result.get());

        assertThat(result.get()).isEqualTo(authorA);
    }

    @Test
    public void testThatAuthorsCanBeDeleted(){
        Author author = TestDataUtil.createTestAuthorA();
        underTest.create(author);
        Optional<Author> result = underTest.findOne(author.getId());
        System.out.println(result.get());


        underTest.delete(author.getId());
        Optional<Author> results = underTest.findOne(author.getId());
        assertThat(results.isEmpty());
        System.out.println(results.isEmpty());


    }
}
