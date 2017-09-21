package my.jdbc.dao.impl;

import my.jdbc.bean.Student;
import my.jdbc.dao.StudentDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by Tomcat on 2017/9/18.
 */
@Component("springjdbcdaoimpl")
public class StudentDaoSpringJdbcTemplateImpl implements StudentDao {
    @Autowired
    NamedParameterJdbcTemplate template;

    @Override
    public Student findStudentbyid(int id) throws Exception {
        String sql = "SELECT id,number,name,age,sex,birthday,cid classid FROM studb where id =?";
        return (Student) template.getJdbcOperations().queryForObject(sql, new Object[]{id}, new BeanPropertyRowMapper(Student.class));
    }

    @Override
    public List<Student> findAllStudent() throws Exception {
        return template.getJdbcOperations().query("SELECT id,number,name,age,sex,birthday,cid classid FROM studb",
                new BeanPropertyRowMapper(Student.class));
    }

    @Override
    public String findStudentNamebyid(int id) throws Exception {
        return template.getJdbcOperations().queryForObject("SELECT name FROM studb where id =?",
                new Object[]{id},
                String.class);
    }

    @Override
    public int addStudent(Student student) throws Exception {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update("INSERT INTO studb VALUES(DEFAULT,:number,:name,:age,:sex,:brithday,:classid)",
                new BeanPropertySqlParameterSource(student),
                keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public int updateStudent(Student student) throws Exception {
        return template.update("UPDATE studb SET number=:number,name=:name,age=:age,sex=:sex,cid=:classid ,birthady=:birthday" +
                        "WHERE id=:id",
                new BeanPropertySqlParameterSource(student));
    }

    @Override
    public int deleteStudent(Student student) throws Exception {
        return template.getJdbcOperations().update("DELETE FROM studb WHERE id =?",
                new Object[]{student.getId()});
    }


}
