package top.wnyeagle.LockWebServer.dao.impl;

import org.junit.jupiter.api.Test;
import top.wnyeagle.LockWebServer.pojo.User;
import top.wnyeagle.LockWebServer.util.DBUtil;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoImplTest {

    private UserDaoImpl userDao = new UserDaoImpl();

    @Test
    void insert() {
        try {
            int rst = userDao.insert(new User(null,"tom","12344",21,2017123105,User.MANAGER,"tom@123.6"));
            DBUtil.getConnection().commit();
            System.out.println(rst);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /*测试用户的id*/
    @Test
    void queryByEmail(){
        try {
            User user = userDao.queryByEmail("tom@123.6");
            System.out.println(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void test2(){
        Integer[] nec = new Integer[10];
        nec[0]=1;nec[1]=2;nec[2]=3;nec[3]=4;
        for (Integer w:nec) {
            System.out.println(w);
        }
    }
}