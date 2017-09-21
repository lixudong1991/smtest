package my.jdbc.connect;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.LinkedList;
import java.util.logging.Logger;


/**
 * Created by Tomcat on 2017/9/16.
 */
public class MyDataSource implements DataSource {
    private String driver;
    private String url;
    private String user ;
    private String pass;
    private LinkedList<Connection> connectionList;
    private int maxCount;
    private int initCount;
    int currentCount = 0;

    public void setDriver(String driverClassName) {
        this.driver = driverClassName;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public void setInitCount(int initCount) {
        this.initCount = initCount;
    }

    public MyDataSource() {

    }

    private void connectpoolinit() {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
        connectionList = new LinkedList<Connection>();
        try {
            for (int i = 0; i < initCount; i++) {
                connectionList.add(createConnect());
                currentCount++;
            }
        } catch (SQLException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    void addConnect(Connection connection) {
        connectionList.add(connection);
    }

    private Connection createConnect() throws SQLException {
        Connection connection = DriverManager.getConnection(url, user, pass);
        ConnectHandler connectHandler = new ConnectHandler(this);
        return connectHandler.bind(connection);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if(connectionList==null) connectpoolinit();
        synchronized (connectionList) {
            if (!connectionList.isEmpty())
                return connectionList.removeFirst();
        }
        throw new SQLException("已没有连接");
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
