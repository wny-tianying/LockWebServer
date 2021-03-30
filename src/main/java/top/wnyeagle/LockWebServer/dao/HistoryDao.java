package top.wnyeagle.LockWebServer.dao;

import top.wnyeagle.LockWebServer.pojo.History;

import java.util.List;

/**
 * @Description
 * @InterfaceName HistoryDao
 * @Author wny
 * @Date 2021/3/5 17:10
 * @Version 1.0
 */

public interface HistoryDao {
    //根据插入数据
    Boolean insertByObject(History history) throws Exception;

    //通过用户的id获取使用记录
    List<History> getHistorysByUserId(Integer userId) throws Exception;

    int deleteByLockAndUserId(Integer userId, Integer lockId) throws Exception;

    int deleteByUserId(Integer userId) throws Exception;

    Boolean delByLockId(Integer lockId) throws Exception;
}
