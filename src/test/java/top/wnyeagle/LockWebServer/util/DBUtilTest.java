package top.wnyeagle.LockWebServer.util;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBUtilTest {

    @Test
    public void testGetConnection(){
        try {
            Connection connection = DBUtil.getConnection();
            System.out.println(connection);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}