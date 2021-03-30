package top.wnyeagle.LockWebServer.util;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtil {
//    将线程与数据库连接进行绑定
    public static ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();

    private static DataSource dataSource;

    /**
     * 静态的初始化数据库连接池
     */
    static {
        try {
            Properties properties = new Properties();
            InputStream asStream = DBUtil.class.getClassLoader().getResourceAsStream("dbConfig.properties");
            properties.load(asStream);
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);//异常往上抛，方便最后的捕获
        }
    }

    public static Connection getConnection() throws SQLException {
        if (threadLocalConnection.get()==null){
            //当前线程没有Connection情况
            Connection con = dataSource.getConnection();
            threadLocalConnection.set(con);
        }
        Connection con = threadLocalConnection.get();
        con.setAutoCommit(false);
        return  con;
    }

    public static void closeResource(Connection con, PreparedStatement ps, ResultSet rst) throws SQLException {
        if (con!=null){
            //在连接回收的时候需要将自动提交设置回true
            con.setAutoCommit(true);
            con.close();
        }
        if (ps!=null){
            ps.close();
        }
        if (rst!=null){
            rst.close();
        }
    }
}
