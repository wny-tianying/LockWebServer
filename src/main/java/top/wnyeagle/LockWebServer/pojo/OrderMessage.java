package top.wnyeagle.LockWebServer.pojo;

import java.sql.Timestamp;

/**
 * @Description
 * @ClassName OrderMessage
 * @Author wny
 * @Date 2021/3/5 17:06
 * @Version 1.0
 */

public class OrderMessage {
    private Integer userId;
    private Integer lockId;
    private Timestamp orderTime;

    public OrderMessage() {
    }

    public OrderMessage(Integer userId, Integer lockId, Timestamp orderTime) {
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
        return "OrderMessage{" +
                "userId=" + userId +
                ", lockId=" + lockId +
                ", orderTime=" + orderTime +
                '}';
    }

}
