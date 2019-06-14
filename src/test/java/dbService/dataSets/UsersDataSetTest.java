package dbService.dataSets;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersDataSetTest {
    @Before
    public void create() {
//        UsersDataSet user = new UsersDataSet("QWERTY", "123");
    }

    @Test
    public void getPass() {
        UsersDataSet user = new UsersDataSet("QWERTY", "123");
        System.out.println(user.getPass());
    }
}