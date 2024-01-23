package com.senich.creditcheck.testcontainers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.testcontainers.containers.PostgreSQLContainer;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class TestContainers {

    private static final String POSTGRES_IMAGE = "postgres:12.8";

    private static final PostgreSQLContainer<?> POSTGRES;

    static {
        POSTGRES = new PostgreSQLContainer<>(POSTGRES_IMAGE).withReuse(true);
    }

    public static void startPostgres() {
        POSTGRES.start();
        System.setProperty("spring.datasource.url", POSTGRES.getJdbcUrl());
        System.setProperty("spring.datasource.username", POSTGRES.getUsername());
        System.setProperty("spring.datasource.password", POSTGRES.getPassword());
        System.setProperty("spring.datasource.driver-class-name", POSTGRES.getDriverClassName());
    }

}
