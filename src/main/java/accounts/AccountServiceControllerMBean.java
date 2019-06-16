package accounts;

@SuppressWarnings("UnusedDeclaration")
public interface AccountServiceControllerMBean {
    int getUsers();

    int getUsersLimit();

    void setUsersLimit(int usersLimit);
}
