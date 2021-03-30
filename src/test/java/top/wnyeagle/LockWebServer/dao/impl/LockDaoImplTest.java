package top.wnyeagle.LockWebServer.dao.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LockDaoImplTest {

    private LockDaoImpl lockDao = new LockDaoImpl();

    @Test
    void isUse() {
        try {
            Boolean use = lockDao.isUse(3);
            System.out.println(use);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}