package microservices4vaadin.aggregate;

import org.axonframework.test.FixtureConfiguration;
import org.axonframework.test.Fixtures;
import org.junit.Before;
import org.junit.Test;

import microservices4vaadin.aggregate.UserAggregate;
import microservices4vaadin.command.CreateUserCommand;
import microservices4vaadin.userevents.UserCreatedEvent;

public class UserAggregateTest {

    private static final long EXPECTED_USER_ID = 1L;
    private static final String EXPECTED_USER_NAME = "UserName";
    private FixtureConfiguration<UserAggregate> fixture;

    @Before
    public void setUp() throws Exception {
        fixture = Fixtures.newGivenWhenThenFixture(UserAggregate.class);
    }

    @Test
    public void testAddProduct() throws Exception {
        UserCreatedEvent user = new UserCreatedEvent();
        user.setId(1L);
        user.setEmail(EXPECTED_USER_NAME);
        fixture.given()
                .when(new CreateUserCommand(EXPECTED_USER_ID, EXPECTED_USER_NAME))
                .expectEvents(user);
    }

}
