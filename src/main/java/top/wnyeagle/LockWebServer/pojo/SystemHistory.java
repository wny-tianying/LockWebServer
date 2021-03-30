package top.wnyeagle.LockWebServer.pojo;

import java.sql.Timestamp;

/**
 * @Description
 * @ClassName SystemHistory
 * @Author wny
 * @Date 2021/3/22 17:40
 * @Version 1.0
 */

public class SystemHistory {
    private Integer userId;
    private Integer lockId;
    private Integer tableId;
    private Timestamp orderTime;
    private Timestamp useStartTime;
    private Timestamp useEndTime;

    public SystemHistory() {
    }

    public SystemHistory(Integer userId, Integer lockId, Integer tableId, Timestamp orderTime, Timestamp useStartTime, Timestamp useEndTime) {
        this.userId = userId;
        this.lockId = lockId;
        this.tableId = tableId;
        this.orderTime = orderTime;
        this.useStartTime = useStartTime;
        this.useEndTime = useEndTime;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
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
        return "SystemHistory{" +
                "userId=" + userId +
                ", lockId=" + lockId +
                ", tableId=" + tableId +
                ", orderTime=" + orderTime +
                ", useStartTime=" + useStartTime +
                ", useEndTime=" + useEndTime +
                '}';
    }
}
