package my.jdbc.template;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomcat on 2017/9/17.
 */
public class MyRowMapper implements RowMapper {
    private Class aClass = null;

    public MyRowMapper(Class aClass) {
        this.aClass = aClass;
    }
    /*
        利用反射实现结果集的映射
     */
    @Override
    public Object getData(ResultSet resultSet) throws Exception{
        if (aClass == null)
            return null;
        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
        int column = resultSetMetaData.getColumnCount();
        List models = new ArrayList();
        while (resultSet.next()) {
            Object model = aClass.newInstance();
            for (int i = 0; i < column; i++) {
                String label = resultSetMetaData.getColumnLabel(i + 1);
                Field f = aClass.getDeclaredField(label);
                String methodname = "set" + label.replace(label.substring(0, 1), label.substring(0, 1).toUpperCase());
                Method method = aClass.getMethod(methodname, f.getType());
                method.invoke(model, resultSet.getObject(label));
            }
            models.add(model);
        }
        int size = models.size();
        return size == 0 ? null : (size == 1 ? models.get(0) : models);
    }
}
