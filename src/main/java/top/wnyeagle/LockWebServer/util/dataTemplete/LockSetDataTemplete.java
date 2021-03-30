package top.wnyeagle.LockWebServer.util.dataTemplete;

import java.sql.Timestamp;

/**
 * @Description 在主界面的设置当中,点击设置过后弹出的信息窗口中显示的数据模板
 * @ClassName LockSetDataTemplete
 * @Author wny
 * @Date 2021/3/10 15:50
 * @Version 1.0
 */

public class LockSetDataTemplete {
    private Integer lockId;
    private Timestamp orderTime;
    private Timestamp useTime;
    private String lockStatus;//只有LockUsingMessage的两种值 IS_USING HAS_ORDERED

    public LockSetDataTemplete() {
    }

    public LockSetDataTemplete(Integer lockId, Timestamp orderTime, Timestamp useTime, String lockStatus) {
        this.lockId = lockId;
        this.orderTime = orderTime;
        this.useTime = useTime;
        this.lockStatus = lockStatus;
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

    public Timestamp getUseTime() {
        return useTime;
    }

    public void setUseTime(Timestamp useTime) {
        this.useTime = useTime;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    @Override
    public String toString() {
        return "LockSetDataTemplete{" +
                "lockId=" + lockId +
                ", orderTime=" + orderTime +
                ", useTime=" + useTime +
                ", lockStatus='" + lockStatus + '\'' +
                '}';
    }
}
