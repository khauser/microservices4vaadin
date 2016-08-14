package lessoria;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestOperations;

import lessoria.rest.BasePersistenceTest;
import microservices4vaadin.auth.AcmeUserDetails;
import microservices4vaadin.controller.CredentialUpdateResource;

@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ApplicationTests extends BasePersistenceTest {

    private static final String EXPECTED_USER_OLD_PASSWORD = "password";

    private static final String EXPECTED_USER_NEW_PASSWORT = "olafson";

    private static final String EXPECTED_USER_EMAIL = "t.tester@lessoria.org";

    private final Long EXPECTED_USER_ID = 1L;

    @Value("${local.server.port}")
    private int port;

    private String userRestUrl;
    private String loginRestUrl;
    private String logoutRestUrl;
    //private String authorizeRestUrl;

    private RestOperations restTemplate = new TestRestTemplate();

    @Before
    public void setUp() {
        userRestUrl = "http://localhost:" + port + "/uaa/user";
        loginRestUrl = "http://localhost:" + port + "/uaa/login";
        logoutRestUrl = "http://localhost:" + port + "/uaa/logout";
        //authorizeRestUrl = "http://localhost:" + port + "/uaa/authorize";
    }

    @Test
    public void homePageProtected() {
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:"
                + port + "/uaa/", String.class);
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    public void loginSucceeds() {
        RequestEntity<MultiValueMap<String, String>> request;
        ResponseEntity<Void> response = doLogin(EXPECTED_USER_OLD_PASSWORD);

        HttpHeaders headers = new HttpHeaders();
        headers.put("COOKIE", response.getHeaders().get("Set-Cookie"));

        request = new RequestEntity<MultiValueMap<String, String>>(
                null, headers, HttpMethod.GET, URI.create(userRestUrl));

        ResponseEntity<AcmeUserDetails> responseUser = restTemplate.exchange(request, AcmeUserDetails.class);
        AcmeUserDetails user = responseUser.getBody();
        assertEquals(EXPECTED_USER_EMAIL, user.getEmail());

        testCredentialUpdate(headers);

        //Now logout
        RequestEntity<Void> requestEntity = new RequestEntity<>(
                null, headers, HttpMethod.GET, URI.create(logoutRestUrl));
        ResponseEntity<Void> responseEntity = restTemplate.exchange(requestEntity, Void.class);
        assertEquals(HttpStatus.FOUND, responseEntity.getStatusCode());

        //And login with new password
        response = doLogin(EXPECTED_USER_NEW_PASSWORT);
    }

    private ResponseEntity<Void> doLogin(String password) {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<String, String>();
        form.set("username", EXPECTED_USER_EMAIL);
        form.set("password", password);
        RequestEntity<MultiValueMap<String, String>> request = new RequestEntity<MultiValueMap<String, String>>(
                form, null, HttpMethod.POST, URI.create(loginRestUrl));
        ResponseEntity<Void> response = restTemplate.exchange(request, Void.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        // in normal environment we would have a referrer here
        assertEquals(null,
                response.getHeaders().getFirst("Location"));
        return response;
    }

    private void testCredentialUpdate(HttpHeaders headers) {
        CredentialUpdateResource credentialUpdateResource = new CredentialUpdateResource();
        credentialUpdateResource.setEmail(EXPECTED_USER_EMAIL);
        credentialUpdateResource.setNewPassword(EXPECTED_USER_NEW_PASSWORT);
        credentialUpdateResource.setOldPassword(EXPECTED_USER_OLD_PASSWORD);

        RequestEntity<CredentialUpdateResource> requestEntity = new RequestEntity<>(
                credentialUpdateResource, headers, HttpMethod.PATCH, URI.create(userRestUrl));
        ResponseEntity<AcmeUserDetails> responseEntity = restTemplate.exchange(requestEntity, AcmeUserDetails.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        AcmeUserDetails user = responseEntity.getBody();
        assertEquals(EXPECTED_USER_ID, Long.valueOf(user.getItemId()));
    }

}
