package servlets;

import accounts.AccountService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class HomePageServlet extends HttpServlet {
    static final Logger logger = LogManager.getLogger(HomePageServlet.class.getName());
    public static final String PAGE_URL = "/home";
    private final AccountService accountService;

    public HomePageServlet(AccountService accountServer) {
        this.accountService = accountServer;
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=utf-8");

        String remove = request.getParameter("remove");

        if (remove != null) {
            accountService.removeUser();
            response.getWriter().println("Hasta la vista!");
            response.setStatus(HttpServletResponse.SC_OK);
            return;
        }

        int limit = accountService.getUsersLimit();
        int count = accountService.getUsersCount();

        logger.info("Limit: {}. Count {}", limit, count);

        if (limit > count) {
            logger.info("User pass");
            accountService.addNewUser();
            response.getWriter().println("Hello, world!");
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            logger.info("User were rejected");
            response.getWriter().println("Server is closed for maintenance!");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
