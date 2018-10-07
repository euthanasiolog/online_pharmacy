package com.epam.pharmacy.web.controller;

import com.epam.pharmacy.web.command.*;
import com.epam.pharmacy.dao.connection.ConnectionPool;
import com.epam.pharmacy.exception.ApplicationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        Command command = factory.getCommand(requestContent);
        CommandResult result = null;
        try {
            result = command.execute(requestContent);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        if (result.getResponseType()== ResponseType.FORWARD){
            req.getRequestDispatcher(result.getPage()).forward(req, resp);
        } else {
            resp.sendRedirect(result.getPage());
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ConnectionPool.getInstance();
    }

    @Override
    public void destroy() {
        super.destroy();
        ConnectionPool connectionPool = ConnectionPool.getInstance();
        connectionPool.closePool();
    }
}
