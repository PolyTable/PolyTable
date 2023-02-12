package kr.ac.kopo.polytable.global.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "kr.ac.kopo")
public class QueryDSLConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory queryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
