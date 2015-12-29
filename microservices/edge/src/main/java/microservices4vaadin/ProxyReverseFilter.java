package microservices4vaadin;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.netflix.util.Pair;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class ProxyReverseFilter extends ZuulFilter {

    private final String locationHeaderName = "Location";

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 7;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext context = RequestContext.getCurrentContext();
        String portalHost = context.getRequest().getServerName();
        String portalProtocol = context.getRequest().getScheme();
        int portalPort = context.getRequest().getServerPort();
        List<Pair<String, String>> responseHeaders = context.getZuulResponseHeaders();
        for (Pair<String, String> entry: responseHeaders){
            if (entry.first().equals(locationHeaderName) && !entry.second().equals(portalHost)) {
                try {
                    URL locationOriginal = new URL(entry.second());
                    String path = locationOriginal.getFile();
                    if (path.startsWith("/ui/"))
                        path = path.replace("/ui/", "/frontend/ui/");
                    URL locationNew = new URL(portalProtocol, portalHost, portalPort, path);
                    entry.setSecond(locationNew.toString());

                    }catch(MalformedURLException e)
                    {
                        return new Object();
                    }
                }

            }

        return new Object();
    }
}
