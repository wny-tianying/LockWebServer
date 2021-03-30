package top.wnyeagle.LockWebServer.util;

import com.google.gson.Gson;

import java.util.List;

/**
 * @Description
 * @ClassName DataUtil
 * @Author wny
 * @Date 2021/3/6 16:50
 * @Version 1.0
 */

public class DataUtil {
    private static Gson gson = new Gson();

    public static String getObjectToString(Object object){
        return gson.toJson(object);
    }

    public static <T> String getListToString(List<T> data,Class T){
        return gson.toJson(data);
    }


    public static Integer[] getStringToInteger(String data){
      return   gson.fromJson(data,Integer[].class);
    }
}
