package com.my.controllers.books;

import com.my.bussiness.beans.Book;
import com.my.bussiness.beans.Quote;
import com.my.dao.BooksDao;
import com.my.dao.QuotesDao;
import com.my.enums.AttributeName;
import com.my.enums.Pages;
import com.my.util.HttpUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.my.util.LogUtil.getCurrentClass;

/**
 * Created with IntelliJ IDEA.
 * User: Vlad
 * Date: 26.02.14
 * Time: 21:15
 * To change this template use File | Settings | File Templates.
 */
public class BookByIdController extends HttpServlet {

    private static final Logger logger = Logger.getLogger(getCurrentClass());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        logger.info("Got request in BookByIdController");

        // domain.com/books/id/42
        String[] pathArray = req.getRequestURI().split("/");
        long id = Long.parseLong(pathArray[3]);
        req.setAttribute(AttributeName.BookToDisplayId, id);

        if (pathArray.length > 4) {
            // domain.com/books/id/42/edit
            String subPath = pathArray[4];
            if (subPath.equals("quotes")) {
                Book book = new Book();
                book.setId(id);
                List<Quote> quoteList = new QuotesDao().selectByBook(book);
                req.setAttribute(AttributeName.QuoteToDisplayList, quoteList);

                logger.info("Request redirected to QuotesByBookView");
                req.setAttribute(AttributeName.CurrentPage, Pages.BookQuotes);
                getServletContext().getRequestDispatcher("/jsp/QuotesByBook.jsp").forward(req, resp);
            } else if (subPath.equals("edit")) {
                logger.debug("Selecting book for editing from DB");
                req.setAttribute(AttributeName.BookToDisplay,
                        new BooksDao().selectById(id));

                logger.info("Request redirecting to BookEditView");
                req.setAttribute(AttributeName.CurrentPage, Pages.BookEdit);
                getServletContext().getRequestDispatcher("/jsp/BookEdit.jsp").forward(req, resp);
            } else if (subPath.equals("delete")) {
                logger.debug("Deleting book with id=" + id + " from DB");
                BooksDao bDao = new BooksDao();
                Book book = bDao.selectById(id);

                bDao.delete(book);

//                String path
                File picture = new File(getServletContext().getRealPath(book.getPictureUrl()));
                if(picture.delete()) {
                    logger.debug("Picture deleted successfully");
                } else {
                    logger.error("Book was deleted but picture was not deleted!");
                }

                logger.debug("Putting delete message to messageMap");
                Map<String, String> messageMap =
                        (Map<String, String>) req.getAttribute(AttributeName.MessagesMap);
                if(messageMap == null) {
                    messageMap = new TreeMap<String, String>();
                }
                messageMap.put("success", "Book have been deleted successfully");
                req.setAttribute(AttributeName.MessagesMap, messageMap);

                logger.info("Request redirected to AllBooksView");
                req.setAttribute(AttributeName.CurrentPage, Pages.AllBooks);
                resp.sendRedirect("/books");
            }
        } else {
            logger.info("Request redirected to BookByIdView");
            req.setAttribute(AttributeName.CurrentPage, Pages.BookById);
            getServletContext().getRequestDispatcher("/jsp/BookById.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("Getting post request in BookByIdController");
        String[] pathArray = req.getRequestURI().split("/");

        if(pathArray.length > 4 && pathArray[4].equals("edit")) {
            logger.debug("Filling book's properties with request parameters");
            Book formedBook = HttpUtil.fillBookWithParams(req);
            formedBook.setId(Long.parseLong(pathArray[3]));

            logger.debug("Passing formed book to update entry in DB");
            long result = new BooksDao().update(formedBook);

            if(result != 0) {
                logger.debug("Book updated, redirecting to this book page");
                req.setAttribute(AttributeName.BookToDisplayId, result);
                resp.sendRedirect("/books/id/" + result);
            } else {
                logger.warn("Error while updating book in DB");
                List<String> errors = Arrays.asList("Error while updating book in DB. Try again later.");
                resp.sendRedirect(req.getRequestURI());
            }
        }
    }
}