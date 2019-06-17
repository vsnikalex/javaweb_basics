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


public class AdminPageServletTest {
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
        HttpServletRequest request = getMockedRequest(AdminPageServlet.PAGE_URL);

        int limit = 10;
        int count = 3;
        when(accountService.getUsersLimit()).thenReturn(limit);
        when(accountService.getUsersCount()).thenReturn(count);

        AdminPageServlet homePage = new AdminPageServlet(accountService);

        homePage.doGet(request, response);

        assertEquals("Limit: " + limit + " Count: " + count, stringWriter.toString().trim());
    }

}
