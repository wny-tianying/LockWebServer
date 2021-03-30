package top.wnyeagle.LockWebServer.pojo;

import java.sql.Timestamp;

/**
 * @Description
 * @ClassName History
 * @Author wny
 * @Date 2021/3/5 16:58
 * @Version 1.0
 */

public class History {
    private Integer userId;
    private Integer lockId;
    private Timestamp orderTime;
    private Timestamp useStartTime;
    private Timestamp useEndTime;


    public History() {
    }

    public History(Integer userId, Integer lockId, Timestamp orderTime, Timestamp useStartTime, Timestamp useEndTime) {
        this.userId = userId;
        this.lockId = lockId;
        this.orderTime = orderTime;
        this.useStartTime = useStartTime;
        this.useEndTime = useEndTime;
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

    public Timestamp getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Timestamp useStartTime) {
        this.useStartTime = useStartTime;
    }

    public Timestamp getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(Timestamp useEndTime) {
        this.useEndTime = useEndTime;
    }

    @Override
    public String toString() {
        return "History{" +
                "userId=" + userId +
                ", lockId=" + lockId +
                ", orderTime=" + orderTime +
                ", useStartTime=" + useStartTime +
                ", useEndTime=" + useEndTime +
                '}';
    }
}
