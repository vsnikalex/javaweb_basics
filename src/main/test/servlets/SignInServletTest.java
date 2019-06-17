package servlets;

import accounts.AccountService;
import accounts.AccountServiceImpl;
import dbService.DBService;
import dbService.DBServiceImpl;
import dbService.dataSets.UsersDataSet;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;


public class SignInServletTest {
    private AccountService accountService = mock(AccountServiceImpl.class);
    private DBService dbService = mock(DBServiceImpl.class);

    private HttpServletResponse getMockedResponse(StringWriter stringWriter) throws IOException {
        HttpServletResponse response = mock(HttpServletResponse.class);

        final PrintWriter writer = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(writer);

        return response;
    }

    private HttpServletRequest getMockedRequest(String url) {
        HttpSession httpSession = mock(HttpSession.class);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getPathInfo()).thenReturn(url);

        return request;
    }

    private UsersDataSet getMockedUserDataSet(String tName, String tPassword) {
        UsersDataSet dataSet = mock(UsersDataSet.class);

        when(dataSet.getName()).thenReturn(tName);
        when(dataSet.getPass()).thenReturn(tPassword);

        return dataSet;
    }

    @Test
    public void nullLogin() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(SignUpServlet.PAGE_URL);

        when(request.getParameter("login")).thenReturn(null);

        SignInServlet signUp = new SignInServlet(dbService, accountService);

        signUp.doPost(request, response);

        assertEquals("Empty field login / password", stringWriter.toString().trim());
    }

    @Test
    public void nullPassword() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(SignUpServlet.PAGE_URL);

        when(request.getParameter("password")).thenReturn(null);

        SignInServlet signUp = new SignInServlet(dbService, accountService);

        signUp.doPost(request, response);

        assertEquals("Empty field login / password", stringWriter.toString().trim());
    }

    @Test
    public void profileDoesNotExist() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(SignUpServlet.PAGE_URL);

        when(request.getParameter("login")).thenReturn("username");
        when(request.getParameter("password")).thenReturn("userPassword");
        when(dbService.getUserByName("username")).thenReturn(null);

        SignInServlet signUp = new SignInServlet(dbService, accountService);

        signUp.doPost(request, response);

        assertEquals("Unauthorized", stringWriter.toString().trim());
    }

    @Test
    public void wrongPassword() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(SignUpServlet.PAGE_URL);

        UsersDataSet dataSet = getMockedUserDataSet("username","rightPassword");

        when(request.getParameter("login")).thenReturn("username");
        when(request.getParameter("password")).thenReturn("wrongPassword");
        when(dbService.getUserByName("username")).thenReturn(dataSet);

        SignInServlet signUp = new SignInServlet(dbService, accountService);

        signUp.doPost(request, response);

        assertEquals("Unauthorized", stringWriter.toString().trim());
    }

    @Test
    public void rightPassword() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(SignUpServlet.PAGE_URL);

        String tName = "username";
        UsersDataSet dataSet = getMockedUserDataSet(tName,"rightPassword");

        when(request.getParameter("login")).thenReturn("username");
        when(request.getParameter("password")).thenReturn("rightPassword");
        when(dbService.getUserByName("username")).thenReturn(dataSet);

        SignInServlet signUp = new SignInServlet(dbService, accountService);

        signUp.doPost(request, response);

        assertEquals("Authorized: " + tName, stringWriter.toString().trim());
    }

}
