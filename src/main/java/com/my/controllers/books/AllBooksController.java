package com.my.controllers.books;

import com.my.bussiness.beans.Book;
import com.my.dao.BooksDao;
import com.my.enums.Pages;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.my.enums.RequestAttributes;
import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.14
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class AllBooksController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in AllBooksController");

        logger.debug("Requesting to BooksDao for all books list");
        List<Book> booksList = new BooksDao().selectAll();
        req.setAttribute(RequestAttributes.BookToDisplayList.name(), booksList);

        logger.info("Request redirected to AllBooksView");
        req.setAttribute(RequestAttributes.CurrentPage.name(), Pages.AllBooks);
        getServletContext().getRequestDispatcher("/jsp/AllBooks.jsp").forward(req, resp);
    }
}
