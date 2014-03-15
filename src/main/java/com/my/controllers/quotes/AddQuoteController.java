package com.my.controllers.quotes;

import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 15.03.14
 * Time: 12:49
 * To change this template use File | Settings | File Templates.
 */
public class AddQuoteController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in AddQuoteController");

        logger.info("Request redirected to AddQuoteView");
        req.setAttribute("currentPage", "addQuote");
        getServletContext().getRequestDispatcher("/jsp/AddQuote.jsp").forward(req, resp);
    }

}