package accounts;

import dbService.dataSets.UsersDataSet;


public interface AccountService {
    void addNewUser();

    void removeUser();

    int getUsersLimit();

    void setUsersLimit(int usersLimit);

    int getUsersCount();

    UsersDataSet getUserBySessionId(String sessionId);

    void addSession(String sessionId, UsersDataSet userProfile);

    void deleteSession(String sessionId);
}
