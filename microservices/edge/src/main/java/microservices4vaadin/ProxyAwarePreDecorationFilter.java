package microservices4vaadin;

import javax.servlet.http.HttpServletRequest;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.constants.ZuulHeaders;
import com.netflix.zuul.context.RequestContext;

public class ProxyAwarePreDecorationFilter extends ZuulFilter {

    private final boolean addProxyHeaders;

    public ProxyAwarePreDecorationFilter(boolean addProxyHeaders) {
        this.addProxyHeaders = addProxyHeaders;
    }

    @Override
    public int filterOrder() {
        // should be after PreDecorationFilter (Order=5)
        return 7;
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public boolean shouldFilter() {
        return addProxyHeaders;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        // expect standard port..
        HttpServletRequest request = ctx.getRequest();
        String proto = request.getHeader(ZuulHeaders.X_FORWARDED_PROTO);
        String host  = request.getHeader("X-Forwarded-Host");
        String port  = request.getHeader("X-Forwarded-Port");

        if(proto != null) {
            ctx.addZuulRequestHeader(ZuulHeaders.X_FORWARDED_PROTO, proto);
        }
        if(host != null) {
            ctx.addZuulRequestHeader("X-Forwarded-Host", host);
        }
        if(port != null) {
            ctx.addZuulRequestHeader("X-Forwarded-Port", port);
        }
        return null;
    }
}