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
import static org.mockito.Mockito.times;


public class SignUpServletTest {
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

    private UsersDataSet getMockedUserDataSet(long tId, String tLogin, String tPassword) {
        UsersDataSet dataSet = mock(UsersDataSet.class);

        when(dataSet.toString()).thenReturn("{" +
                "id=" + tId +
                ", name='" + tLogin + '\'' +
                ", password='" + tPassword + '\'' +
                '}');

        return dataSet;
    }

    @Test
    public void testRegistration() throws Exception {
        final StringWriter stringWriter = new StringWriter();
        HttpServletResponse response = getMockedResponse(stringWriter);
        HttpServletRequest request = getMockedRequest(SignUpServlet.PAGE_URL);

        String tLogin = "testLogin";
        String tPassword = "testPassword";
        when(request.getParameter("login")).thenReturn(tLogin);
        when(request.getParameter("password")).thenReturn(tPassword);

        long tId = 2;
        UsersDataSet dataSet = getMockedUserDataSet(tId, tLogin, tPassword);
        when(dbService.addUser(tLogin, tPassword)).thenReturn(tId);
        when(dbService.getUser(tId)).thenReturn(dataSet);

        SignUpServlet signUp = new SignUpServlet(dbService, accountService);

        signUp.doPost(request, response);

        assertEquals("Added user data set: {id=2, name='testLogin', password='testPassword'}", stringWriter.toString().trim());
        verify(dbService, times(1)).addUser(tLogin, tPassword);
        verify(dbService, times(1)).getUser(tId);
    }

}
