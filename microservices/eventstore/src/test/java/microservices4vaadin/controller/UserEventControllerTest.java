package microservices4vaadin.controller;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;

import microservices4vaadin.controller.UserEventController;
import microservices4vaadin.userevents.UserCreatedEvent;

public class UserEventControllerTest {

    UserEventController userEventController;
    MockHttpServletResponse mockHttpServletResponse;

    @Mock
    CommandGateway gateway;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        userEventController = new UserEventController();
        mockHttpServletResponse = new MockHttpServletResponse();
    }

    @Test
    public void testAddWithGoodRequestParams() {
        // Arrange
        userEventController.commandGateway = gateway; //cheating a bit here, but mocking all the axon framework's beans is a pain.
        when(gateway.sendAndWait(any())).thenReturn(null);

        UserCreatedEvent userCreatedEvent = new UserCreatedEvent();
        userCreatedEvent.setId(1L);
        userCreatedEvent.setEmail("Test Create User");
        //Act
        userEventController.publishCreatedEvent(userCreatedEvent, mockHttpServletResponse);

        //Assert
        verify(gateway).sendAndWait(any());
        assertTrue(mockHttpServletResponse.getStatus() == HttpServletResponse.SC_CREATED);
    }

}
