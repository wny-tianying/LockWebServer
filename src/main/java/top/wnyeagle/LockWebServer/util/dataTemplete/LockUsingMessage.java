package top.wnyeagle.LockWebServer.util.dataTemplete;

/**
 * @Description 锁的使用信息，就是在主界面需要展示的数据：锁的序号、
 *                                              锁的状态：正在使用 和 已经预约
 * @ClassName LockUsingMessage
 * @Author wny
 * @Date 2021/3/9 17:07
 * @Version 1.0
 */

public class LockUsingMessage {
    public static final String IS_USING = "正在使用";
    public static final String HAS_ORDERED = "已经预约";
//    锁的id
    private Integer lockId;
//    锁的使用状态
    private String lockStatus;

    public LockUsingMessage() {
    }

    public LockUsingMessage(Integer lockId, String lockStatus) {
        this.lockId = lockId;
        this.lockStatus = lockStatus;
    }

    public static String getIsUsing() {
        return IS_USING;
    }

    public static String getHasOrdered() {
        return HAS_ORDERED;
    }

    public Integer getLockId() {
        return lockId;
    }

    public void setLockId(Integer lockId) {
        this.lockId = lockId;
    }

    public String getLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(String lockStatus) {
        this.lockStatus = lockStatus;
    }

    @Override
    public String toString() {
        return "LockUsingMessage{" +
                "lockId=" + lockId +
                ", lockStatus='" + lockStatus + '\'' +
                '}';
    }
}
