package microservices4vaadin.test;

import microservices4vaadin.AuthserverApplication;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;

@TestExecutionListeners({DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class, DbUnitTestExecutionListener.class})
@DbUnitConfiguration(databaseConnection="testDataSource")
@DatabaseSetup(DatabaseIntegrationTest.DATASET)
@DatabaseTearDown(type = DatabaseOperation.DELETE_ALL, value = { DatabaseIntegrationTest.EMPTY_DATASET })
@SpringApplicationConfiguration(classes = AuthserverApplication.class)
@ActiveProfiles("test")
public class DatabaseIntegrationTest {
    protected static final String DATASET = "classpath:lessor_resource_test_dataset.xml";
    protected static final String EMPTY_DATASET = "classpath:lessor_empty_dataset.xml";
}
