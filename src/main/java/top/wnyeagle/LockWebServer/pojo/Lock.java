package top.wnyeagle.LockWebServer.pojo;

/**
 * @Description
 * @ClassName Lock
 * @Author wny
 * @Date 2021/3/5 16:45
 * @Version 1.0
 */

public class Lock {
    private Integer lockId;
    private Boolean lockUsingStatus;
    private Boolean lockWorkingStatus;
    private Boolean ableToUse;


    public Lock() {
    }

    public Lock(Integer lockId, Boolean lockUsingStatus, Boolean lockWorkingStatus, Boolean ableToUse) {
        this.lockId = lockId;
        this.lockUsingStatus = lockUsingStatus;
        this.lockWorkingStatus = lockWorkingStatus;
        this.ableToUse = ableToUse;
    }

    public Integer getLockId() {
        return lockId;
    }

    public void setLockId(Integer lockId) {
        this.lockId = lockId;
    }

    public Boolean getLockUsingStatus() {
        return lockUsingStatus;
    }

    public void setLockUsingStatus(Boolean lockUsingStatus) {
        this.lockUsingStatus = lockUsingStatus;
    }

    public Boolean getLockWorkingStatus() {
        return lockWorkingStatus;
    }

    public void setLockWorkingStatus(Boolean lockWorkingStatus) {
        this.lockWorkingStatus = lockWorkingStatus;
    }

    public Boolean getAbleToUse() {
        return ableToUse;
    }

    public void setAbleToUse(Boolean ableToUse) {
        this.ableToUse = ableToUse;
    }

    @Override
    public String toString() {
        return "Lock{" +
                "lockId=" + lockId +
                ", lockUsingStatus=" + lockUsingStatus +
                ", lockWorkingStatus=" + lockWorkingStatus +
                ", ableToUse=" + ableToUse +
                '}';
    }
}
