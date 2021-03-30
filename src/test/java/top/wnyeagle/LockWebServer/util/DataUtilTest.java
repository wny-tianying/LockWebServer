package top.wnyeagle.LockWebServer.util;

import org.junit.jupiter.api.Test;
import top.wnyeagle.LockWebServer.util.dataTemplete.LockUsingMessage;

import java.util.ArrayList;
import java.util.List;

class DataUtilTest {

    @Test
    void getObjectToString() {
        List<LockUsingMessage> data = new ArrayList<>();
        data.add(new LockUsingMessage(1,LockUsingMessage.IS_USING));
        data.add(new LockUsingMessage(2,LockUsingMessage.HAS_ORDERED));
        System.out.println(DataUtil.getListToString(data, LockUsingMessage.class));
    }
}