package ru.students.tpu.config;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.students.tpu.dao.GroupDao;
import ru.students.tpu.dao.StudentDao;

import javax.sql.DataSource;

@Configuration
public class JdbiConfig {
    @Bean
    public Jdbi jdbi(DataSource dataSource) {
        return Jdbi.create(dataSource)
                .installPlugin(new SqlObjectPlugin());
    }

    @Bean
    public StudentDao studentDao(Jdbi jdbi) {
        return jdbi.onDemand(StudentDao.class);
    }

    @Bean
    public GroupDao groupDao(Jdbi jdbi) {
        return jdbi.onDemand(GroupDao.class);
    }
}
