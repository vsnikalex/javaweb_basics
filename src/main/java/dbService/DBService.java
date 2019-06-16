package dbService;

import dbService.dataSets.UsersDataSet;


public interface DBService {
    public UsersDataSet getUser(long id) throws DBException;

    public UsersDataSet getUserByName(String name) throws DBException;

    public long addUser(String name, String password) throws DBException;

    public void printConnectInfo();
}
