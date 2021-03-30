package top.wnyeagle.LockWebServer.dao;

import top.wnyeagle.LockWebServer.pojo.History;
import top.wnyeagle.LockWebServer.pojo.SystemHistory;

import java.util.List;

/**
 * @Description
 * @InterfaceName SystemHistoryDao
 * @Author wny
 * @Date 2021/3/22 17:42
 * @Version 1.0
 */

public interface SystemHistoryDao {
    Integer delByUserId(Integer userId) throws Exception;

    Boolean delByLockId(Integer tem) throws Exception;

    List<SystemHistory> getAllObject() throws Exception;

    void insertByHistory(History history) throws Exception;

    SystemHistory getObjectByTableId(Integer tableId) throws Exception;

    Boolean delByTableId(Integer tableId) throws Exception;
}
