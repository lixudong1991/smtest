package my.jdbc.connect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
/**
 * Created by Tomcat on 2017/9/16.
 */
public class ConnectHandler implements InvocationHandler{
    private Connection realConnection=null;
    private MyDataSource dataSource=null;
    private Connection warpedConnect=null;
    private int maxCount=5;
    private int useCount=0;
    ConnectHandler(MyDataSource dataSource){
        this.dataSource=dataSource;
    }
    Connection bind(Connection relconn){
        warpedConnect=(Connection) Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class[]{Connection.class},this);
        realConnection=relconn;
        return warpedConnect;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if("close".equals(method.getName())){
            if(++useCount<maxCount)
            dataSource.addConnect(warpedConnect);
            else{
                realConnection.close();
                dataSource.currentCount--;
            }
            return null;
        }
        return method.invoke(realConnection,args);
    }
}

