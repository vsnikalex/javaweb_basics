package accounts;

import dbService.dataSets.UsersDataSet;

import java.util.HashMap;
import java.util.Map;


public class AccountService {
    private final Map<String, UsersDataSet> loginToProfile;
    private final Map<String, UsersDataSet> sessionIdToProfile;

    public AccountService() {
        loginToProfile = new HashMap<>();
        sessionIdToProfile = new HashMap<>();
    }

    public void addNewUser(UsersDataSet userProfile) {
        loginToProfile.put(userProfile.getName(), userProfile);
    }

    public UsersDataSet getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

    public UsersDataSet getUserBySessionId(String sessionId) {
        return sessionIdToProfile.get(sessionId);
    }

    public void addSession(String sessionId, UsersDataSet userProfile) {
        sessionIdToProfile.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessionIdToProfile.remove(sessionId);
    }
}
