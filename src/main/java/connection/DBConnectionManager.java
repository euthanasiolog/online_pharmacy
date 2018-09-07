package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.concurrent.locks.ReentrantLock;

public class DBConnectionManager {
    private int poolSize;
    private String url;
    private String user;
    private String password;
    private Connection connection;
    private final static String USER = "mysqlDB.user";
    private final static String URL = "mysqlDB.url";
    private final static String PASSWORD = "mysqlDB.password";
    private final static String POOL_SIZE = "mysqlDB.poolsize";
    private final static ResourceBundle BUNDLE = ResourceBundle.getBundle("mysqlDB.properties");
    private static DBConnectionManager instance = new DBConnectionManager();
    private static ReentrantLock lock = new ReentrantLock();


    private DBConnectionManager (){
            this.url = BUNDLE.getString(URL);
            this.password = BUNDLE.getString(PASSWORD);
            this.user = BUNDLE.getString(USER);
            this.poolSize = new Integer(BUNDLE.getString(POOL_SIZE));
    }

    public static DBConnectionManager getInstance(){
        if (instance==null){
            lock.lock();
            try {
                if (instance==null){
                    instance = new DBConnectionManager();
                    return instance;
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection(){
        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace(); // fix after add Logger
        }
        return connection;
    }

    public int getPoolSize() {
        return poolSize;
    }
}
