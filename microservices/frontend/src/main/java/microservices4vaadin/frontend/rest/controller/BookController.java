package microservices4vaadin.frontend.rest.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;

import microservices4vaadin.frontend.rest.resource.dto.Book;

@Controller
public class BookController extends AbstractRestController {
    public final static String TEST_CLASS_REST_URL = "/test";

//    @Autowired
//    private RestUtils restUtils;

//    @HystrixCommand
    public Book findOne(Long testId) {
        Book book = new Book();
        book.setName("My book of life");
        book.setReleaseDate(LocalDate.now());
        return book;
//        log.debug("Setting up REST call to get a bookingClass for itemId='{}'", bookingClassItemId.toString());
//
//        RequestEntity<Void> request = RequestEntity.get(URI.create(restUtils.getServiceUrl("backend") + BOOKING_CLASS_REST_URL + "/" + bookingClassItemId.toString())).accept(MediaTypes.HAL_JSON).build();
//        ResponseEntity<BookingClassResource> bookingClassResourceEntity = getRestTemplate().exchange(request, new ParameterizedTypeReference<BookingClassResource>() {});
//
//        BookingClassResource bookingClassResource = bookingClassResourceEntity.getBody();
//        BookingClassDTO bookingClassDTO = bookingClassResource.getContent();
//        bookingClassDTO.setRelationalLinks(bookingClassResource.getLinks());
//        return bookingClassDTO;
    }

}
