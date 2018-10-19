package com.epam.pharmacy.weblayer.controller;

import com.epam.pharmacy.dao.connection.ConnectionPool;
import com.epam.pharmacy.dao.connection.ConnectionPoolException;
import com.epam.pharmacy.weblayer.command.*;
import com.epam.pharmacy.dao.connection.ConnectionPoolImpl;
import com.epam.pharmacy.exception.ApplicationException;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebServlet("/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestContent requestContent = new RequestContent(req);
        CommandFactory factory = CommandFactory.getInstance();
        if (factory.getCommand(requestContent) != null) {
            Command command = factory.getCommand(requestContent);
            CommandResult result = null;
            try {
                result = command.execute(requestContent);
            } catch (ApplicationException e) {
                log.error("application error", e);
                resp.sendError(500);
            }
            if (result != null) {
                if (ResponseType.FORWARD.equals(result.getResponseType())){
                    req.getRequestDispatcher(result.getPage()).forward(req, resp);
                } else {
                    resp.sendRedirect(result.getPage());
                }
            } else {
                log.error("service return null!!! Need to fix immediately!!!");
                resp.sendError(500);
            }
        } else {
            log.error("request has no command!");
            resp.sendError(500);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
        } catch (ConnectionPoolException e) {
            log.error("error get pool");
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            ConnectionPool connectionPool = ConnectionPoolImpl.getInstance();
            connectionPool.closePool();
        } catch (ConnectionPoolException e) {
            log.error("error close pool", e);
        }
    }
}
