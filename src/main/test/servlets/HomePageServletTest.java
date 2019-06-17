package servlets;

import accounts.AccountService;
import accounts.AccountServiceImpl;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;


public class HomePageServletTest {
    private AccountService accountService = mock(AccountServiceImpl.class);

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

    @Test
    public void testRemoveUser() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(HomePageServlet.PAGE_URL);
        when(request.getParameter("remove")).thenReturn("");

        HomePageServlet homePage = new HomePageServlet(accountService);

        homePage.doGet(request, response);

        assertEquals("Hasta la vista!", stringWriter.toString().trim());
        verify(accountService, times(1)).removeUser();
    }

    @Test
    public void testAddUser() throws Exception {
        when(accountService.getUsersLimit()).thenReturn(10);

        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(HomePageServlet.PAGE_URL);
        when(request.getParameter("remove")).thenReturn(null);

        HomePageServlet homePage = new HomePageServlet(accountService);

        homePage.doGet(request, response);

        assertEquals("Hello, world!", stringWriter.toString().trim());
        verify(accountService, times(1)).addNewUser();
    }

    @Test
    public void testExceedUsersLimit() throws Exception {
        when(accountService.getUsersLimit()).thenReturn(0);

        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(HomePageServlet.PAGE_URL);
        when(request.getParameter("remove")).thenReturn(null);

        HomePageServlet homePage = new HomePageServlet(accountService);

        homePage.doGet(request, response);

        assertEquals("Server is closed for maintenance!", stringWriter.toString().trim());
        verify(accountService, times(0)).addNewUser();
    }

    @Test
    public void testDoubleAdd() throws Exception {
        when(accountService.getUsersLimit()).thenReturn(2);

        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(HomePageServlet.PAGE_URL);
        when(request.getParameter("remove")).thenReturn(null);

        HomePageServlet homePage = new HomePageServlet(accountService);

        for (int i = 0; i < 2; i++) {
            homePage.doGet(request, response);
        }

        // if user refreshes his homepage, it won't cause usersCount++
        verify(accountService, times(1)).addNewUser();
    }
}
