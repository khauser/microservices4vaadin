package microservices4vaadin;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URI;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import microservices4vaadin.config.CustomAuthenticationSuccessHandler;
import microservices4vaadin.configuration.PersistenceTestContext;
import microservices4vaadin.test.DatabaseIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceTestContext.class})
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationTests extends DatabaseIntegrationTest {

    //private final String EXPECTED_FIRST_NAME = "Udo";

    @Value("${local.server.port}")
    private int port;

    private RestTemplate template = new TestRestTemplate();

    @Test
    public void homePageProtected() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/uaa/", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void authorizationRedirects() {
        ResponseEntity<String> response = template.getForEntity("http://localhost:"
                + port + "/uaa/oauth/authorize", String.class);
        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        String location = response.getHeaders().getFirst("Location");
        assertTrue("Wrong header: " + location,
                location.startsWith("http://localhost:" + port + "/uaa/login"));
    }

    @Test
    public void loginSucceeds() {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.set("username", "t.tester@microservice4vaadin.org");
        form.set("password", "password");
        RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(
                form, null, HttpMethod.POST, URI.create("http://localhost:" + port
                        + "/uaa/login"));
        ResponseEntity<Void> response = template.exchange(request, Void.class);
        assertEquals(CustomAuthenticationSuccessHandler.DEFAULT_TARGET_URL,
                response.getHeaders().getFirst("Location"));

//        HttpHeaders headers = new HttpHeaders();
//        headers.put("COOKIE", response.getHeaders().get("Set-Cookie"));
//
//        request = new RequestEntity<MultiValueMap<String, String>>(
//                form, null, HttpMethod.GET, URI.create("http://localhost:" + port
//                        + "/uaa/user"));
//
//        ResponseEntity<AcmeUserDetails> responseUser = template.exchange(request, AcmeUserDetails.class);
//        AcmeUserDetails user = responseUser.getBody();
//        assertEquals(EXPECTED_FIRST_NAME, user.getFirstName());
    }

}
