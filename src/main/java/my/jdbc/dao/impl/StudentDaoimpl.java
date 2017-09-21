package my.jdbc.dao.impl;

import my.jdbc.bean.Student;
import my.jdbc.dao.StudentDao;
import my.jdbc.template.MyRowMapper;
import my.jdbc.template.Mytemplate;
import my.jdbc.template.RowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by Tomcat on 2017/9/17.
 */
@Component("mydaoimpl")
public class StudentDaoimpl implements StudentDao {
    @Autowired
    Mytemplate mytemplate;

    @Override
    public Student findStudentbyid(int id) throws Exception {
        String sql = "SELECT id,number,name,age,sex,cid classid FROM studb where id =?";
        return (Student) mytemplate.getDateFromDb(sql, new Object[]{id}, new MyRowMapper(Student.class));
    }

    @Override
    public List<Student> findAllStudent() throws Exception {
        String sql = "SELECT id,number,name,age,sex,cid classid FROM studb";
        return (List<Student>) mytemplate.getDateFromDb(sql, null, new MyRowMapper(Student.class));
    }

    @Override
    public String findStudentNamebyid(int id) throws Exception {
        String sql = "SELECT name FROM studb where id =?";
        return (String) mytemplate.getDateFromDb(sql, new Object[]{id}, new RowMapper() {
            @Override
            public Object getData(ResultSet rs) throws Exception {
                if (rs.next())
                    return rs.getString("name");
                return null;
            }
        });
    }

    @Override
    public int addStudent(Student student) throws Exception {
        String sql = "INSERT INTO studb VALUES(DEFAULT,?,?,?,?,?,?)";
        Object[] args = new Object[]{student.getNumber(), student.getName(), student.getAge(), student.getSex(),student.getBirthday(),
                student.getClassid()};
        return mytemplate.update(sql, args);
    }

    @Override
    public int updateStudent(Student student) throws Exception {
        return 0;
    }

    @Override
    public int deleteStudent(Student student) throws Exception {
        return 0;
    }

}
