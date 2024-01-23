package com.senich.creditcheck;

import com.senich.creditcheck.testcontainers.TestContainers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
public class AbstractTest {

    @BeforeAll
    static void startTestContainers() {
        TestContainers.startPostgres();
    }

    @AfterAll
    static void stopTestContainers() {
        TestContainers.stopPostgres();
    }
}
