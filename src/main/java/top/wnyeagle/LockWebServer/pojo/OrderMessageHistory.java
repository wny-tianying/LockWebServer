package top.wnyeagle.LockWebServer.pojo;

import java.sql.Timestamp;

/**
 * @Description 主要起一个临时的信息存储的作用，在预约与使用之间临时存储预约的时间数据
 * @ClassName OrderMessageHistory
 * @Author wny
 * @Date 2021/3/11 10:11
 * @Version 1.0
 */

public class OrderMessageHistory {
    private Integer userId;
    private Integer lockId;
    private Timestamp orderTime;

    public OrderMessageHistory() {
    }

    public OrderMessageHistory(Integer userId, Integer lockId, Timestamp orderTime) {
        this.userId = userId;
        this.lockId = lockId;
        this.orderTime = orderTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getLockId() {
        return lockId;
    }

    public void setLockId(Integer lockId) {
        this.lockId = lockId;
    }

    public Timestamp getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Timestamp orderTime) {
        this.orderTime = orderTime;
    }

    @Override
    public String toString() {
        return "OrderMessageHistory{" +
                "userId=" + userId +
                ", lockId=" + lockId +
                ", orderTime=" + orderTime +
                '}';
    }
}
