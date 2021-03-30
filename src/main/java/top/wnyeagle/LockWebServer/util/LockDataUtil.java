package top.wnyeagle.LockWebServer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description 该类的作用是存储有关储物柜和客户端的数据交互的数据
 * @ClassName LockDataUtil
 * @Author wny
 * @Date 2021/3/12 17:53
 * @Version 1.0
 */

public class LockDataUtil {
    //该变量的作用是存储需要打开的锁（开始使用时候的）
    private static List<Integer> needToOpenStart = new ArrayList<>();
    //该变量的作用是存储需要关闭的锁（开始使用时候的）
    private static List<Integer> hasClosedStart = new ArrayList<>();

    /**
     * 对开始的锁的id集合进行添加操作，主要是手机的移动端
     * @param lockId
     */
    public static void addStartLocks(Integer lockId){
        synchronized(needToOpenStart){
            needToOpenStart.add(lockId);
        }
    }

    /**
     * 该方法主要是控制柜的一端需要需要访问请求,访问在使用的时候需要开的锁
     * @return
     */
    public static List<Integer> getStartLocks(){
        synchronized (needToOpenStart){
            List<Integer> data = new ArrayList<>();
            needToOpenStart.forEach(tem->{
                data.add(tem);
            });
            needToOpenStart.clear();
            return data;
        }
    }

    /**
     * 控制柜端访问该函数将已经关闭的柜门的锁号存入hasClosedStart集合当中
     * @param lockIds
     */
    public static void insertIntoHasClosedStart(List<Integer> lockIds){
        synchronized (hasClosedStart){
            lockIds.forEach(tem->{
                hasClosedStart.add(tem);
            });
        }
    }

    /**
     * 该方法是判断当前的锁是否已经正常的关闭，也就是判断当前的id是否存在于hasClosedStart中，
     * 若存在，就将其取出，返回true；不在就返回false 移动端访问
     * @return
     */
    public static  Boolean  isExitHasClosedStart(Integer lockId){
        synchronized (hasClosedStart){
            if (hasClosedStart.contains(lockId)){
                hasClosedStart.remove(lockId);
                return true;
            }
            return false;
        }
    }


}
