package WELLET.welletServer.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.util.Collection;

@Slf4j
@Configuration
public class CookieAttributeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        chain.doFilter(request, response);
        log.info("CookieAttributeFilter");
        addSameSite(httpServletResponse, "None");

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException{
    }

    @Override
    public void destroy(){
    }

    private void addSameSite(HttpServletResponse response, String sameSite){
        Collection<String> headers = response.getHeaders(HttpHeaders.SET_COOKIE);
        boolean firstHeader = true;
        for(String header : headers){
            if(firstHeader){
                response.setHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
                firstHeader = false;
                continue;
            }
            response.addHeader(HttpHeaders.SET_COOKIE, String.format("%s; Secure; %s", header, "SameSite=" + sameSite));
        }
    }
}