package microservices4vaadin.rest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestOperations;

import microservices4vaadin.persistence.User;

@WebAppConfiguration
@IntegrationTest("server.port:0")
public class UserResourceIntegrTest extends BasePersistenceTest {

    private static final Long EXPECTED_USER_ID = 1L;
    private static final Long EXPECTED_USER_ID_NEW = 3L;
    private static final String EXPECTED_USER_FIRST_NAME = "Teo";
    private static final String EXPECTED_USER_LAST_NAME = "Olsen";
    private static final String EXPECTED_USER_MODIFIED_BY = "testsystem";
    private static final String EXPECTED_USER_MODIFIED_DATE_TIME = "1952-01-23 12:23:22";

    private static final String EXPECTED_USER_FIRST_NAME_2 = "Olga";

    private static final String EXPECTED_USER_LANGUAGE = "de";


    @Value("${local.server.port}")
    private int port;

    private String userRestUrl;

    @Autowired
    private RestOperations restTemplate;

    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        userRestUrl = "http://localhost:" + port + "/user";
    }

    @Test
    public void testFind() {
        ResponseEntity<Resource<User>> responseEntity = restTemplate.exchange(
                userRestUrl + "/" + EXPECTED_USER_ID, HttpMethod.GET
                , null, new ParameterizedTypeReference<Resource<User>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        //assertEquals(MediaTypes.HAL_JSON, responseEntity.getHeaders().getContentType());
        User user = responseEntity.getBody().getContent();
        assertEquals(EXPECTED_USER_FIRST_NAME, user.getFirstName());
        assertEquals(EXPECTED_USER_LAST_NAME, user.getLastName());
        assertEquals(EXPECTED_USER_MODIFIED_BY, user.getLastModifiedBy());
        assertEquals(LocalDateTime.parse(EXPECTED_USER_MODIFIED_DATE_TIME, DATE_FORMAT), user.getLastModifiedDateTime());
        assertEquals(EXPECTED_USER_LANGUAGE, user.getLanguage());
    }

    @Test
    public void testCreate() {
        UserUpdateResource user = new UserUpdateResource();
        user.setItemId(EXPECTED_USER_ID_NEW);
        user.setFirstName(EXPECTED_USER_FIRST_NAME_2);
        user.setLastName(EXPECTED_USER_LAST_NAME);
        user.setCreatedBy(EXPECTED_USER_MODIFIED_BY);
        user.setCreatedDateTime(LocalDateTime.parse(EXPECTED_USER_MODIFIED_DATE_TIME, DATE_FORMAT));
        user.setLanguage(EXPECTED_USER_LANGUAGE);

        User respUser = restTemplate.postForObject(userRestUrl, new HttpEntity<UserUpdateResource>(user), User.class);

        assertNotNull(respUser);
        assertEquals(EXPECTED_USER_ID_NEW, respUser.getItemId());
        assertEquals(EXPECTED_USER_FIRST_NAME_2, respUser.getFirstName());
        assertEquals(EXPECTED_USER_LAST_NAME, respUser.getLastName());
    }

//    @Test
//    public void testCreateConstraintViolation() {
//        UserUpdateResource user = new UserUpdateResource();
//        user.setFirstName(EXPECTED_USER_FIRST_NAME_2);
//        user.setLastName(EXPECTED_USER_LAST_NAME);
//
//        try {
//            restTemplate.exchange(userRestUrl
//                    , HttpMethod.POST, new HttpEntity<UserUpdateResource>(user)
//                    , User.class);
//            fail("ConstraintViolationException should have been thrown");
//        } catch (ConstraintViolationException violationException) {
//            // then
//            final Set<ConstraintViolation<?>> constraintViolations = violationException.getConstraintViolations();
//            assertEquals(constraintViolations.size(), 2);
//        }
//    }
//
    @Test
    public void testUpdate() {
        UserUpdateResource userUpdateResource = new UserUpdateResource();
        userUpdateResource.setFirstName(EXPECTED_USER_FIRST_NAME_2);
        userUpdateResource.setLastName(EXPECTED_USER_LAST_NAME);
        restTemplate.exchange(userRestUrl + "/" + EXPECTED_USER_ID, HttpMethod.PATCH
                , new HttpEntity<UserUpdateResource>(userUpdateResource)
                , UserUpdateResource.class);

        ResponseEntity<Resource<User>> responseEntity = restTemplate.exchange(userRestUrl
                + "/" + EXPECTED_USER_ID, HttpMethod.GET
                , null, new ParameterizedTypeReference<Resource<User>>() {});

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        User user = responseEntity.getBody().getContent();
        assertEquals(EXPECTED_USER_ID, user.getItemId());
        assertEquals(EXPECTED_USER_FIRST_NAME_2, user.getFirstName());
        assertEquals(EXPECTED_USER_LAST_NAME, user.getLastName());
    }

    @Test
    public void testDelete() {
        restTemplate.delete(userRestUrl + "/" + EXPECTED_USER_ID);

        thrown.expect(HttpClientErrorException.class);
        restTemplate.exchange(
                userRestUrl + "/" + EXPECTED_USER_ID, HttpMethod.GET
                , null, new ParameterizedTypeReference<Resource<User>>() {});
    }

}
