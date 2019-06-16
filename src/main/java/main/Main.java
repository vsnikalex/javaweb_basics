package main;

import accounts.*;
import dbService.DBService;
import dbService.DBServiceImpl;

import servlets.AdminPageServlet;
import servlets.HomePageServlet;
import servlets.SignInServlet;
import servlets.SignUpServlet;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;


public class Main {
    static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            logger.error("Use port as the first argument");
            System.exit(1);
        }

        String portString = args[0];
        int port = Integer.valueOf(portString);

        logger.info("Starting at http://127.0.0.1:" + portString);

        DBService dbService = new DBServiceImpl();
        AccountService accountService = new AccountServiceImpl();

        AccountServiceControllerMBean serverStatistics = new AccountServiceController(accountService);
        MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
        ObjectName name = new ObjectName("Admin:type=AccountServerController.usersLimit");
        mbs.registerMBean(serverStatistics, name);

        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(new SignUpServlet(dbService, accountService)), SignUpServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new SignInServlet(dbService, accountService)), SignInServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new AdminPageServlet(accountService)), AdminPageServlet.PAGE_URL);
        context.addServlet(new ServletHolder(new HomePageServlet(accountService)), HomePageServlet.PAGE_URL);

        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(true);
        resource_handler.setResourceBase("public_html");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, context});
        server.setHandler(handlers);

        server.start();
        logger.info("Server started");

        server.join();
    }
}
