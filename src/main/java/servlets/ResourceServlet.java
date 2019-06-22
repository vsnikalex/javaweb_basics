package servlets;

import sax.ReadXMLFileSAX;
import resources.ResourceServer;
import resources.TestResource;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ResourceServlet extends HttpServlet {
    public static final String PAGE_URL = "/resources";
    private ResourceServer resourceServer;

    public ResourceServlet(ResourceServer resourceServer) {
        this.resourceServer = resourceServer;
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException, IOException {

        String path = request.getParameter("path");
        TestResource newTestResource = (TestResource) ReadXMLFileSAX.readXML(path);
        resourceServer.setTestResource(newTestResource);

    }

}
