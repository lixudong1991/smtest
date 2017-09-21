package my.jdbc.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by Tomcat on 2017/9/17.
 */
@Component
public class Mytemplate {
    @Autowired
    private DataSource dataSource;
    public Object getDateFromDb(String sql, Object[] args, RowMapper rowMapper) throws Exception {
        ResultSet rs = null;
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn= dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ParameterMetaData parameterMetaData = ps.getParameterMetaData();
            int parameterCount = parameterMetaData.getParameterCount();
            if (parameterCount > 0) {
                for (int i = 0; i < parameterCount; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }
            rs = ps.executeQuery();
            return rowMapper.getData(rs);
        } catch (Exception e) {
           throw e;
        } finally {
            free(conn, ps, rs);
        }
    }

    public int update(String sql, Object[] args) throws SQLException {
        ResultSet rs = null;
        Connection conn=null;
        PreparedStatement ps=null;
        try {
            conn= dataSource.getConnection();
            ps = conn.prepareStatement(sql);
            ParameterMetaData parameterMetaData = ps.getParameterMetaData();
            int parameterCount = parameterMetaData.getParameterCount();
            if (parameterCount > 0) {
                for (int i = 0; i < parameterCount; i++) {
                    ps.setObject(i + 1, args[i]);
                }
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw e;
        } finally {
            free(conn, ps,null);
        }
    }
    private void free(Connection conn, Statement st, ResultSet resultSet) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (st != null) {
                    st.close();
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                }catch (SQLException e2){
                    e2.printStackTrace();
                }
            }
        }
    }
}
