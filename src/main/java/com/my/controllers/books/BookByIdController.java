package com.my.controllers.books;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.14
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class BookByIdController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        getServletContext().getNamedDispatcher("bookByIdView").forward(req, resp);
    }
}