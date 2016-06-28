package microservices4vaadin;

import java.util.List;
import java.util.Set;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

@Order(2)
@Component
@SuppressWarnings("unchecked")
public class CorrectHeadersFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 10000;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext currentContext = RequestContext.getCurrentContext();

        currentContext.addZuulRequestHeader("host",  currentContext.getRequest()
                .getHeader("host"));

        // XXX
        List<Pair<String, String>> responseHeaders = currentContext.getZuulResponseHeaders();
        responseHeaders.remove("x-forwarded-prefix");

        ((Set<String>) currentContext.get("ignoredHeaders")).clear();// XXX
        return null;
    }

}
