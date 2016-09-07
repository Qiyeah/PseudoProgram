package com.ex.qi.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * Created by sunline on 2016/8/18.
 */
@WebFilter(filterName = "encoding",urlPatterns = "/*",initParams = {@WebInitParam(name = "encoding",value = "utf-8")})
public class CharsetFilter implements Filter {
    private String encoding = null;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (null != encoding){
            servletRequest.setCharacterEncoding(encoding);
            servletResponse.setContentType("text/html;charset="+encoding);
        }
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        encoding = null;
    }
}
