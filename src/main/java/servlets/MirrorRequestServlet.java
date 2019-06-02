package servlets;

import templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MirrorRequestServlet extends HttpServlet {

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        response.getWriter().println(PageGenerator.instance().getPage("mirror.html", pageVariables));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {

        Map<String, Object> pageVariables = createPageVariablesMap(request);

        response.setContentType("text/html;charset=utf-8");

        response.getWriter().println(PageGenerator.instance().getPage("mirror.html", pageVariables));
    }

    private static Map<String, Object> createPageVariablesMap(HttpServletRequest request){
        Map<String, Object> pageVariables = new HashMap<>();

        Map<String, String[]> map = request.getParameterMap();
        Object firstKey = map.keySet().toArray()[0];

        pageVariables.put("parameter", map.get(firstKey)[0]);

        return pageVariables;
    }
}
