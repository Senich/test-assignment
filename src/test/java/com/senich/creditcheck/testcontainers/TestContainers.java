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
        System.setProperty("spring.r2dbc.url", "r2dbc:postgresql://" + POSTGRES.getHost() + ":"
            + POSTGRES.getFirstMappedPort() + "/" + POSTGRES.getDatabaseName());
        System.setProperty("spring.r2dbc.username", POSTGRES.getUsername());
        System.setProperty("spring.r2dbc.password", POSTGRES.getPassword());
        System.setProperty("spring.datasource.driver-class-name", POSTGRES.getDriverClassName());
    }

    public static void stopPostgres() {
        POSTGRES.start();
    }

}
