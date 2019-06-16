package accounts;

import dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;


public class AccountServiceImpl implements AccountService {
    private int usersCount;
    private int usersLimit;
    private final Map<String, UsersDataSet> sessionIdToProfile;

    // default usersLimit = 10
    public AccountServiceImpl() {
        this(10);
    }

    public AccountServiceImpl(int usersLimit) {
        this.usersCount = 0;
        this.usersLimit = usersLimit;
        sessionIdToProfile = new HashMap<>();
    }

    @Override
    public void addNewUser() {
        usersCount += 1;
    }

    @Override
    public void removeUser() {
        usersCount -= 1;
    }

    @Override
    public int getUsersLimit() {
        return usersLimit;
    }

    @Override
    public void setUsersLimit(int usersLimit) {
        this.usersLimit = usersLimit;
    }

    @Override
    public int getUsersCount() {
        return usersCount;
    }

    @Override
    public UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    @Override
    public void addSession(String sessionId, UsersDataSet userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    @Override
    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
