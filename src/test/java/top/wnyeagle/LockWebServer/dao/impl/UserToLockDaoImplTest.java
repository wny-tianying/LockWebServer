package top.wnyeagle.LockWebServer.dao.impl;

import org.junit.jupiter.api.Test;
import top.wnyeagle.LockWebServer.dao.UserToLockDao;
import top.wnyeagle.LockWebServer.pojo.UserToLock;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserToLockDaoImplTest {
    private UserToLockDao user = new UserToLockDaoImpl();
    @Test
    void getDatasByUserId() {
        try {
            List<UserToLock> datasByUserId = user.getDatasByUserId(3);
            datasByUserId.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}