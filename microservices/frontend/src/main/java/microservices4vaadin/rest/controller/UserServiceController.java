package microservices4vaadin.rest.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.client.RestOperations;

import microservices4vaadin.rest.controller.RestUtils;
import microservices4vaadin.rest.resource.dto.UserServiceUserDTO;
import microservices4vaadin.rest.resource.update.UserServiceUserUpdateResource;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class UserServiceController {

    public final static String USER_REST_URL = "/user";

    @Autowired
    private RestOperations restTemplate;

    @Autowired
    private RestUtils restUtils;

    public UserServiceUserDTO findOne(Long userItemId) {
        log.debug("Setting up REST call to get a user for itemId='{}'", userItemId.toString());

        RequestEntity<Void> request = RequestEntity.get(URI.create(restUtils.getServiceUrl("userservice") + USER_REST_URL + "/" + userItemId.toString())).accept(MediaTypes.HAL_JSON).build();
        ResponseEntity<Resource<UserServiceUserDTO>> userResourceEntity = restTemplate.exchange(request, new ParameterizedTypeReference<Resource<UserServiceUserDTO>>() {});

        Resource<UserServiceUserDTO> userResource = userResourceEntity.getBody();
        UserServiceUserDTO userDTO = userResource.getContent();
        return userDTO;
    }

    public void delete(Long id) {
        restTemplate.delete(restUtils.getServiceUrl("userservice") + USER_REST_URL + "/" + id);
    }

    public void update(UserServiceUserDTO dto) {
        log.debug("Setting up REST call to update an account");
        restTemplate.exchange(restUtils.getServiceUrl("userservice") + USER_REST_URL + "/" + dto.getItemId()
            , HttpMethod.PATCH, new HttpEntity<UserServiceUserUpdateResource>(_syncResource(dto)),
                UserServiceUserUpdateResource.class);
    }

    public UserServiceUserDTO create(UserServiceUserDTO inputDto) {
        log.debug("Setting up REST call to create an account");
        RequestEntity<UserServiceUserUpdateResource> request = RequestEntity.post(URI.create(restUtils.getServiceUrl("userservice")
                + USER_REST_URL)).accept(MediaTypes.HAL_JSON)
                .contentType(MediaType.APPLICATION_JSON).body(_syncResource(inputDto));
        ResponseEntity<Resource<UserServiceUserDTO>> resourceEntity = restTemplate.exchange(request
                , new ParameterizedTypeReference<Resource<UserServiceUserDTO>>() {});

        Resource<UserServiceUserDTO> resource = resourceEntity.getBody();
        UserServiceUserDTO dto = resource.getContent();
        return dto;
    }

    private UserServiceUserUpdateResource _syncResource(UserServiceUserDTO dto) {
        UserServiceUserUpdateResource resource = new UserServiceUserUpdateResource();
        resource.setFirstName(dto.getFirstName());
        resource.setLastName(dto.getLastName());
        resource.setGender(dto.getGender().toString());
        resource.setTitle(dto.getTitle());
        resource.setLastModifiedBy(dto.getLastModifiedBy());
        resource.setLastModifiedDateTime(dto.getLastModifiedDateTime());
        resource.setLanguage(dto.getLanguage());
        return resource;
    }

}
