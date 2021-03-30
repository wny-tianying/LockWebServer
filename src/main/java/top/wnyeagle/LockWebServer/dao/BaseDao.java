package top.wnyeagle.LockWebServer.dao;

import top.wnyeagle.LockWebServer.util.DBUtil;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description 所有的基础的数据库操作，包括修改，查询
 * @ClassName BaseDao
 * @Author wny
 * @Date 2021/3/5 11:02
 * @Version 1.0
 */

public  class BaseDao<T> {

    private Class<T> clazz;

    {
//        this是指new的子类对象
        Type superclass = this.getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) superclass;
//        获取父类的泛型参数表
        Type[] arguments = parameterizedType.getActualTypeArguments();
        clazz =(Class<T>) arguments[0];
    }

    /**
     * 通用的修改方法
     * @param con 传入当前线程的Connection
     * @param sql 传入修改的修改语句
     * @param args 占位符的参数，与占位符保持一致
     * @return 返回影响的行数(无则为0)
     * @throws SQLException
     */
    public int update(Connection con,String sql,Object...args) throws SQLException {
        PreparedStatement ps = null;
        int effectLines = 0;
        try {
            ps = con.prepareStatement(sql);
            effectLines = 0;
            for (int i=0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            effectLines = ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        } finally {
            DBUtil.closeResource(null,ps,null);
        }
        return effectLines;
    }

    /**
     * 获取单个对象数据的通用查询
     * @param con 传入当前线程的Connection
     * @param sql 传入修改的修改语句
     * @param args 占位符的参数，与占位符保持一致
     * @return 返回查到的数据，无则为空null
     * @throws SQLException
     */
    public T getSingleObject(Connection con,String sql,Object...args) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rst =null;
        try {
            ps = con.prepareStatement(sql);
            for (int i= 0;i<args.length;i++){
                ps.setObject(i+1,args[i]);
            }
            rst =  ps.executeQuery();
//        获取结果集的元数据
            ResultSetMetaData metaData = rst.getMetaData();
//        获取结果集的列数
            int columnCount = metaData.getColumnCount();
            while(rst.next()){
                T t = clazz.newInstance();
                for (int i=0;i<columnCount;i++){
                    Object columnValue = rst.getObject(i+1);
                    //获取每个列的列名
                    String columnName = metaData.getColumnName(i+1);
                    //给具体的对象指定的columnName属性赋值
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t,columnValue);
                }
                return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeResource(null,ps,rst);
        }
        return null;
    }

    /**
     * 获取多个对象数据的通用查询
     * @param con 传入当前线程的Connection
     * @param sql 传入修改的修改语句
     * @param args 占位符的参数，与占位符保持一致
     * @return  返回List
     * @throws SQLException
     */
    public List<T> getForList(Connection con, String sql, Object ...args) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> data = new ArrayList<T>();
        try {
            ps = con.prepareStatement(sql);
            if (args!=null) {
                for(int i=0;i<args.length;i++) {
                    ps.setObject(i+1, args[i]);
                }
            }
            rs = ps.executeQuery();
//		获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
//		通过ResultSetMetaData获取结果集的列数
            int columnCount = rsmd.getColumnCount();

            while(rs.next()) {
                //动态的创建对象
                T t= clazz.newInstance();
                for(int i=0;i<columnCount;i++) {
//				处理结果集一行数据中的每一列
                    Object columnValue = rs.getObject(i+1);
//				获取每个列的列名
                    String columnName  = rsmd.getColumnLabel(i+1);
//				给customers对象指定columnName属性赋值为
                    Field field = clazz.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }
                data.add(t);
            }
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            DBUtil.closeResource(null, ps, rs);
            return data;
        }
    }

    /**
     * 返回单一的数据
     * @param con 传入当前线程的Connection
     * @param sql 传入修改的修改语句
     * @param args 占位符的参数，与占位符保持一致
     * @param <E> 传回的参数的类型
     * @return 返回的是查询结果
     */
    public <E> E getValue(Connection con, String sql, Object ...args) throws SQLException {
        E data = null;
        PreparedStatement ps = null;
        ResultSet rst = null;
        try {
            ps = con.prepareStatement(sql);
            if (args!=null) {
                for(int i=0;i<args.length;i++) {
                    ps.setObject(i+1, args[i]);
                }
            }
            rst = ps.executeQuery();
            while (rst.next()){
               data = (E) rst.getObject(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }finally {
            DBUtil.closeResource(null,ps,rst);
        }

        return data;
    }

}
