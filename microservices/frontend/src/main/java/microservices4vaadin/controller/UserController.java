package microservices4vaadin.controller;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//
//import lombok.extern.slf4j.Slf4j;
//import microservices4vaadin.auth.AcmeUserDetails;
//
//@Controller
//@Slf4j
//public class UserController extends AbstractRestController {
//    public final static String BOOKING_CLASS_REST_URL = "/bookingClass";
//
//    @Autowired
//    private RestUtils restUtils;
//
////    @HystrixCommand
//    public AcmeUserDetails findOne(Long userId) {
////        log.debug("Setting up REST call to get a bookingClass for itemId='{}'", bookingClassItemId.toString());
////
////        RequestEntity<Void> request = RequestEntity.get(URI.create(restUtils.getServiceUrl("backend") + BOOKING_CLASS_REST_URL + "/" + bookingClassItemId.toString())).accept(MediaTypes.HAL_JSON).build();
////        ResponseEntity<BookingClassResource> bookingClassResourceEntity = getRestTemplate().exchange(request, new ParameterizedTypeReference<BookingClassResource>() {});
////
////        BookingClassResource bookingClassResource = bookingClassResourceEntity.getBody();
////        BookingClassDTO bookingClassDTO = bookingClassResource.getContent();
////        bookingClassDTO.setRelationalLinks(bookingClassResource.getLinks());
////        return bookingClassDTO;
//        return null;
//    }
//
//}
