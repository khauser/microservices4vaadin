package microservices4vaadin;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ProxyRouteLocator;
import org.springframework.stereotype.Controller;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ZuulApplication.class, ProxyAwarePreDecorationFilterIntegrationTest.TestController.class })
@ActiveProfiles("test")
@WebAppConfiguration
@IntegrationTest
@DirtiesContext
public class ProxyAwarePreDecorationFilterIntegrationTest {

    @Value("${local.server.port}")
    int port;

    @Autowired
    private ProxyRouteLocator routes;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(context).build();
        routes.addRoute("/test-request/**", "http://localhost:" + port + "/test-controller/x-forward");
    }

    @Test
    public void should_add_xforward_headers() throws Exception {
        this.mockMvc.perform(get("/test-request/") //
                    .header("X-Forwarded-Port", "8443") //
                    .header("X-Forwarded-Host", "somehost") //
                    .header("X-Forwarded-Proto", "https") //
                ) //
                .andDo(print()) //
                .andExpect(status().isOk()) //
                .andExpect(header().string("X-P1", is("8443"))) //
                .andExpect(header().string("X-P2", is("https"))) //
                .andExpect(header().string("X-P3", is("somehost"))) //
                ;
    }

    @Controller
    static class TestController {
        @RequestMapping(value = "/test-controller/x-forward", method = RequestMethod.GET)
        public void test( //
                @RequestHeader(value = "X-Forwarded-Proto", required = false) String proto, //
                @RequestHeader(value = "X-Forwarded-Port", required = false) String port, //
                @RequestHeader(value = "X-Forwarded-Host", required = false) String host, //
                HttpServletResponse response) {
            response.setHeader("X-P1", port);
            response.setHeader("X-P2", proto);
            response.setHeader("X-P3", host);
        }
    }

}

