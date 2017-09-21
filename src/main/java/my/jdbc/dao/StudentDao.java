package my.jdbc.dao;

import my.jdbc.bean.Student;

import java.util.List;

/**
 * Created by Tomcat on 2017/9/17.
 */
public interface StudentDao {
    Student findStudentbyid(int id)throws Exception;
    List<Student> findAllStudent()throws Exception;
    String findStudentNamebyid(int id)throws Exception;
    int addStudent(Student student)throws Exception;
    int updateStudent(Student student)throws Exception;
    int deleteStudent(Student student) throws Exception;
}
