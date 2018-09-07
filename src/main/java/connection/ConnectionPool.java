package connection;

import java.sql.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.ReentrantLock;

public final class ConnectionPool {
    private static BlockingQueue<PooledConnection> connectionPool;
    private static DBConnectionManager dbConnectionManager;
    private static int poolSize;
    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPool instance = new ConnectionPool();
    private ConnectionPool (){
        dbConnectionManager = DBConnectionManager.getInstance();
        poolSize = dbConnectionManager.getPoolSize();
        connectionPool = new LinkedBlockingQueue<>(poolSize);
        initPool();
    }

    public static ConnectionPool getInstance() {
        if (instance==null){
            lock.lock();
            try {
                if (instance==null){
                    instance = new ConnectionPool();
                    return instance;
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private static void initPool(){
        for (int i=0;i<poolSize;i++){
            Connection connection = dbConnectionManager.getConnection();
            PooledConnection pooledConnection = new PooledConnection(connection);
            connectionPool.add(pooledConnection);
        }
    }

    public Connection takeConnection() throws ConnectionPoolException {
//        for (int i=0;i<10000;i++){
            if (!connectionPool.isEmpty()){
                try {
                    return connectionPool.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();// fix after add Logger
                }
            } else {
                takeConnection();
            }
//        }
        throw  new ConnectionPoolException( "Connection time out", new Exception());
    }

    private void putConnection(Connection connection){
        try {
            connectionPool.put((PooledConnection) connection);
        } catch (InterruptedException e) {
            e.printStackTrace();// fix after add Logger
        }
    }

    public void closeConnection (Connection connection, PreparedStatement statement, ResultSet resultSet){
        putConnection(connection);
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();// fix after add Logger
        }
        try {
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();// fix after add Logger
        }
    }

    public void closeConnection (Connection connection, PreparedStatement statement){
        putConnection(connection);
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();// fix after add Logger
        }
    }

    public void closePool (){
        while (!connectionPool.isEmpty()){
            try {
                connectionPool.take().realyClose();
            } catch (InterruptedException e) {
                e.printStackTrace();// fix after add Logger
            }
        }
    }

}
