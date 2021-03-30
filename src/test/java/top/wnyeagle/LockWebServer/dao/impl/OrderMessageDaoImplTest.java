package top.wnyeagle.LockWebServer.dao.impl;

import org.junit.jupiter.api.Test;
import top.wnyeagle.LockWebServer.dao.OrderMessageDao;
import top.wnyeagle.LockWebServer.pojo.OrderMessage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderMessageDaoImplTest {
    private OrderMessageDao orderMessageDao = new OrderMessageDaoImpl();
    @Test
    void getOrdersByUserId() {
        try {
            List<OrderMessage> ordersByUserId = orderMessageDao.getOrdersByUserId(3);
            ordersByUserId.forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}