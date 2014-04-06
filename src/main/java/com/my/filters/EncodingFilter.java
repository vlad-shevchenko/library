package com.my.filters;

import org.apache.log4j.Logger;

import javax.servlet.*;
import java.io.IOException;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 30.03.14
 * Time: 15:22
 * To change this template use File | Settings | File Templates.
 */
public class EncodingFilter implements Filter {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    private static final String ENCODING_DEFAULT = "UTF-8";
    private static final String ENCODING_INIT_PARAM_NAME = "encoding";

    private String encoding;

    public void destroy(){
    }

    public void doFilter(ServletRequest req, ServletResponse resp,
                         FilterChain chain) throws ServletException, IOException{
        logger.debug("Setting encoding to " + encoding);
        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);
        String contentType = req.getContentType();
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException{
        encoding = config.getInitParameter(ENCODING_INIT_PARAM_NAME);
        if (encoding == null)
            encoding = ENCODING_DEFAULT;
    }

}
