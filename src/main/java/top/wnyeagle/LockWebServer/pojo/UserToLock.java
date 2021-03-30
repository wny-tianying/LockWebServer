package top.wnyeagle.LockWebServer.pojo;

import java.sql.Timestamp;

/**
 * @Description
 * @ClassName UserToLock
 * @Author wny
 * @Date 2021/3/5 17:02
 * @Version 1.0
 */

public class UserToLock {
    private Integer userId;
    private Integer lockId;
    private Timestamp useTime;

    public UserToLock() {
    }

    public UserToLock(Integer userId, Integer lockId, Timestamp useTime) {
        this.userId = userId;
        this.lockId = lockId;
        this.useTime = useTime;
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

    public Timestamp getUseTime() {
        return useTime;
    }

    public void setUseTime(Timestamp useTime) {
        this.useTime = useTime;
    }

    @Override
    public String toString() {
        return "UserToLock{" +
                "userId=" + userId +
                ", lockId=" + lockId +
                ", useTime=" + useTime +
                '}';
    }
}
