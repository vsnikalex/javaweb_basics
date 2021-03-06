package servlets;

import accounts.AccountService;

import dbService.DBException;
import dbService.DBService;
import dbService.dataSets.UsersDataSet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class SignUpServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(SignUpServlet.class.getName());
    public static final String PAGE_URL = "/signup";
    private final AccountService accountService;
    private final DBService dbService;

    public SignUpServlet(DBService dbService, AccountService accountService) {
        this.dbService = dbService;
        this.accountService = accountService;
    }

    //sign up
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        try {
            long userId = dbService.addUser(request.getParameter("login"), request.getParameter("password"));
            UsersDataSet dataSet = dbService.getUser(userId);
            response.getWriter().println("Added user data set: " + dataSet);
        } catch (DBException e) {
            e.printStackTrace();
        }

    }

}
