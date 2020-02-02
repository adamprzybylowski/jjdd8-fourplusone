package com.infoshareacademy.web.servlet;

import com.infoshareacademy.domain.entity.Book;
import com.infoshareacademy.domain.view.BookView;
import com.infoshareacademy.freemarker.TemplateProvider;
import com.infoshareacademy.service.BookService;
import com.infoshareacademy.service.UploaderService;
import com.infoshareacademy.service.RatingService;
import com.infoshareacademy.service.ReservationService;
import com.infoshareacademy.service.UserService;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/single")
public class SingleBookServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(SingleBookServlet.class.getName());

    @Inject
    private BookService bookService;

    @Inject
    private TemplateProvider templateProvider;

    @Inject
    private ReservationService reservationService;

    @Inject
    private RatingService ratingService;

    @Inject
    private UserService userService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = (String) req.getSession().getAttribute("name");
        String email = (String) req.getSession().getAttribute("email");
        String role = (String) req.getSession().getAttribute("role");

        String param = req.getParameter("id");

        req.getSession().setAttribute("book_id", Long.parseLong(param));

        if (param == null || param.isEmpty()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        Long id = Long.valueOf(param);

        Book book = bookService.getById(id);

        boolean isReserved = true;
        boolean isRated = true;

        if (reservationService.findReservationByBook(book).isEmpty()) {
            isReserved = false;
        }
        if (req.getSession().getAttribute("email") == null) {
            isReserved = true;
        }

        //if (ratingService.checkIsRated(userService.))

        PrintWriter writer = resp.getWriter();

        Template template = templateProvider
                .getTemplate(getServletContext(),
                        "singlePage.ftlh");
        Map<String, Object> model = new HashMap<>();

        BookView bookView = bookService.getBookViewById(id);

        model.put("book", bookView);
        model.put("isReserved", isReserved);

        if (email != null && !email.isEmpty()) {
            model.put("logged", "yes");
            model.put("email", email);
        } else {
            model.put("logged", "no");}
        if(role != null && role.equals("superadmin")) {
            model.put("superadmin", "yes");
        }
        else {model.put("superadmin", "no");}
            try {
                template.process(model, writer);
            } catch (TemplateException e) {
                logger.error("Template error");
            }
        }
    }
