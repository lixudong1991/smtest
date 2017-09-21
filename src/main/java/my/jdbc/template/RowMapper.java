package my.jdbc.template;

import java.sql.ResultSet;

/**
 * Created by Tomcat on 2017/9/17.
 */
public interface RowMapper {
    Object getData(ResultSet rs) throws Exception;
}
