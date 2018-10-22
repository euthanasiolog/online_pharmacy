package com.epam.pharmacy.weblayer.filter;

import com.epam.pharmacy.util.constant.PagePath;
import com.epam.pharmacy.util.constant.ProjectConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter( urlPatterns = { "/jsp/*" },
        initParams = { @WebInitParam(name = ProjectConstant.INDEX, value = PagePath.INDEX) })
public class PageSecurityFilter implements Filter {
    private String indexPath;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        indexPath = filterConfig.getInitParameter(ProjectConstant.INDEX);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        if (httpRequest.getParameter(ProjectConstant.PAGE) != null) {
            indexPath = httpRequest.getParameter(ProjectConstant.PAGE);
        }
        httpRequest.setAttribute(ProjectConstant.SECURITY_REDIRECT, "true");
        httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
