package com.didispace.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.log4j.Logger;
import org.springframework.cloud.netflix.zuul.ZuulFilterInitializer;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xjx on 2018/11/16.
 */
public class AccessFilter extends ZuulFilter {

    private final Logger logger = Logger.getLogger(AccessFilter.class);

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext cxt = RequestContext.getCurrentContext();
        HttpServletRequest request = cxt.getRequest();

        //logger.info("send {} request to {}",request.getMethod(),request.getRequestURL().toString());
        String accessToken = request.getParameter("accessToken");
        if (accessToken == null){
            logger.warn("accessToken is empty");
            cxt.setSendZuulResponse(true);
            cxt.setResponseBody("token不能为空");
            cxt.setResponseStatusCode(401);
            return null;
        }
        logger.info("accessToken ok");
        return null;
    }
}
