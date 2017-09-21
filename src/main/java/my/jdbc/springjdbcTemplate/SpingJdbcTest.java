package my.jdbc.springjdbcTemplate;

import my.jdbc.bean.Student;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.sql.*;
import java.util.List;

/**
 * Created by Tomcat on 2017/9/17.
 */
public class SpingJdbcTest {
    JdbcTemplate jdbcTemplate;
    NamedParameterJdbcTemplate nameJdbcTemplate;
    @Before
    public void setUp() {
        ApplicationContext context=new ClassPathXmlApplicationContext("AppCon.xml");
        jdbcTemplate = (JdbcTemplate) context.getBean("JdbcTemplate");
        nameJdbcTemplate= (NamedParameterJdbcTemplate) context.getBean("namedParameterJdbcTemplate");
    }

    @Test
    public void findStudentId() {
        String sql = "SELECT number,name,age,sex,cid classid FROM studb where id =?";
        Student student = null;
        try {
            student = (Student) jdbcTemplate.queryForObject(sql, new Object[]{5}, new BeanPropertyRowMapper(Student.class));
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        System.out.println(student);
    }

    @Test
    public void findStudents() {
        String sql = "SELECT number,name,age,sex,cid classid FROM studb";
        List students = (List) jdbcTemplate.query(sql, new BeanPropertyRowMapper(Student.class));
        System.out.println(students);
    }

    @Test
    public void findStudentCount() {
        String sql = "SELECT count(*) age FROM studb";
        int count = jdbcTemplate.queryForObject(sql,Integer.TYPE);
        System.out.println(count);
    }
    @Test
    public void findStudentsbyBean() {
        Student student=new Student(null,113,17,"female",4);
        String sql = "SELECT id,name,number,age,sex,cid classid  FROM studb " +
                "where number>:number AND age>:age AND sex=:sex AND cid=:classid";
        SqlParameterSource sqlParameterSource=new BeanPropertySqlParameterSource(student);
        List students=nameJdbcTemplate.query(sql,sqlParameterSource,new BeanPropertyRowMapper(Student.class));
        System.out.println(students);
    }
    @Test
    public void jdbcTemplateGetPrimarykeyAfterInsert() {
        Student student=new Student("bb",127,27,"female",4);
        String sql = "INSERT INTO studb(id,name,number,age,sex,cid) VALUES (DEFAULT,?,?,?,?,?)";
        jdbcTemplate.execute(new ConnectionCallback() {
            @Override
            public Object doInConnection(Connection connection) throws SQLException, DataAccessException {
                PreparedStatement preparedStatement=connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1,student.getName());
                preparedStatement.setInt(2,student.getNumber());
                preparedStatement.setInt(3,student.getAge());
                preparedStatement.setString(4,student.getSex());
                preparedStatement.setInt(5,student.getClassid());
                preparedStatement.executeUpdate();
                ResultSet resultSet=preparedStatement.getGeneratedKeys();
                if(resultSet.next()) {
                   student.setId(resultSet.getInt(1));
                }
                return null;
            }
        });
        System.out.println(student);
    }
    @Test
    public void nameJdbcTemplateGetPrimarykeyAfterInsert() {
        Student student=new Student("ga",126,24,"male",3);
        String sql = "INSERT INTO studb(id,name,number,age,sex,cid) VALUES (DEFAULT,:name,:number,:age,:sex,:classid)";
        SqlParameterSource sqlParameterSource=new BeanPropertySqlParameterSource(student);
        KeyHolder keyHolder=new GeneratedKeyHolder();
        nameJdbcTemplate.update(sql,sqlParameterSource,keyHolder);
        student.setId(keyHolder.getKey().intValue());
        System.out.println(student);
    }

}
