package org.kentut.postgretutorial;

import org.kentut.postgretutorial.domain.Author;
import org.kentut.postgretutorial.domain.Book;

public final class TestDataUtil {

    private TestDataUtil(){

    }


    public static Author createTestAuthorA() {
        return Author.builder()
                .id(1L)
                .name("Kenzie Harsanto")
                .age(22)
                .build();
    }
    public static Author createTestAuthorB() {
        return Author.builder()
                .id(2L)
                .name("Boboboy")
                .age(20)
                .build();
    }
    public static Author createTestAuthorC() {
        return Author.builder()
                .id(3L)
                .name("Ip Man")
                .age(12)
                .build();
    }

    public static Book createTestBookA() {
        return Book.builder()
                .isbn("087-776-161-1712")
                .title("Happily Ever After")
                .authorId(1L)
                .build();
    }

    public static Book createTestBookB() {
        return Book.builder()
                .isbn("087-776-161-2631")
                .title("Happily Ever Before")
                .authorId(2L)
                .build();
    }

    public static Book createTestBookC() {
        return Book.builder()
                .isbn("087-776-161-8761")
                .title("Happily Ever")
                .authorId(3L)
                .build();
    }
}
