package top.wnyeagle.LockWebServer.util.dataTemplete;

/**
 * @Description 该类的作用是管理员界面的锁的设置弹窗需要的所模板信息
 * @ClassName ManagerLockMsg
 * @Author wny
 * @Date 2021/3/25 18:46
 * @Version 1.0
 */

public class ManagerLockMsg {
    private Integer lockId;
    //分为 ‘已经预约’ 和 '没有预约'
    private Boolean isOrdered;
    private String  orderPerson;
    private Boolean ableToUse;

    public ManagerLockMsg() {
    }

    public ManagerLockMsg(Integer lockId, Boolean isOrdered, String orderPerson, Boolean ableToUse) {
        this.lockId = lockId;
        this.isOrdered = isOrdered;
        this.orderPerson = orderPerson;
        this.ableToUse = ableToUse;
    }

    public Integer getLockId() {
        return lockId;
    }

    public void setLockId(Integer lockId) {
        this.lockId = lockId;
    }

    public Boolean getOrdered() {
        return isOrdered;
    }

    public void setOrdered(Boolean ordered) {
        isOrdered = ordered;
    }

    public String getOrderPerson() {
        return orderPerson;
    }

    public void setOrderPerson(String orderPerson) {
        this.orderPerson = orderPerson;
    }

    public Boolean getAbleToUse() {
        return ableToUse;
    }

    public void setAbleToUse(Boolean ableToUse) {
        this.ableToUse = ableToUse;
    }

    @Override
    public String toString() {
        return "ManagerLockMsg{" +
                "lockId=" + lockId +
                ", isOrdered=" + isOrdered +
                ", orderPerson='" + orderPerson + '\'' +
                ", ableToUse=" + ableToUse +
                '}';
    }
}
