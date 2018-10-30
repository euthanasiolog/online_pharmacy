package com.epam.pharmacy.dao.connection;

import lombok.extern.log4j.Log4j2;
import java.sql.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

@Log4j2
public final class ConnectionPoolImpl implements ConnectionPool {
    private BlockingQueue<PooledConnection> connectionPoolQueue;
    private BlockingQueue<PooledConnection> busyConnections;
    private DbConnectionManager dbConnectionManager = new DbConnectionManager();
    private int poolSize;
    private static ReentrantLock lock = new ReentrantLock();
    private static ConnectionPoolImpl instance;
    private static AtomicBoolean isActive = new AtomicBoolean();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        if (instance!=null){
            throw new CloneNotSupportedException();
        }
        return super.clone();
    }

    private ConnectionPoolImpl() throws ConnectionPoolException {
        if (instance!=null){
            throw new ConnectionPoolException("pool is actually created", new RuntimeException());
        }
        poolSize = dbConnectionManager.getPoolSize();
        connectionPoolQueue = new LinkedBlockingQueue<>(poolSize);
        busyConnections = new LinkedBlockingQueue<>(poolSize);
        isActive.set(true);
        initPool();
    }

    public static ConnectionPoolImpl getInstance() throws ConnectionPoolException{
        if (!isActive.get()){
            lock.lock();
            try {
                if (instance==null) {
                    instance = new ConnectionPoolImpl();
                }
            } catch (ConnectionPoolException e) {
                log.error("error create instance of pool", e);
                throw new ConnectionPoolException("error create instance of pool", e);
            } finally {
                isActive.set(true);
                lock.unlock();
            }
        }
        return instance;
    }

    private  void initPool(){
        for (int i=0;i<poolSize;i++){
            Connection connection = dbConnectionManager.getConnection();
            PooledConnection pooledConnection = new PooledConnection(connection);
            connectionPoolQueue.add(pooledConnection);
        }
    }

    public Connection getConnection() {
        try {
            PooledConnection connection = connectionPoolQueue.take();
            busyConnections.add(connection);
            return connection;
            } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return null;
    }

    void putConnection(Connection connection) {
        try {
            if (busyConnections.remove(connection)) {
                connectionPoolQueue.put((PooledConnection) connection);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void closePool () throws ConnectionPoolException {
       if (!busyConnections.isEmpty()) {
           for (PooledConnection connection : busyConnections) {
               connection.realyClose();
           }
       }

       for (int i=0;i<poolSize;i++){
            try {
                connectionPoolQueue.take().realyClose();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

}
