package org.kentut.postgretutorial;

import lombok.Data;
import lombok.extern.java.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
@Log
public class PostgreTutorialApplication implements CommandLineRunner {
    private final DataSource dataSource;

    public PostgreTutorialApplication(final DataSource dataSource){
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication.run(PostgreTutorialApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        log.info("Data Source: "+ dataSource.toString());
        final JdbcTemplate restTemplate = new JdbcTemplate(dataSource);
        restTemplate.execute("select 1");
    }
}
