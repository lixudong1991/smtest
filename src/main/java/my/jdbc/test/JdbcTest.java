package my.jdbc.test;


import my.jdbc.bean.Student;
import my.jdbc.dao.StudentDao;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by Tomcat on 2017/9/14.
 */
public class JdbcTest {
    StudentDao studentDao;

    @Before
    public void setUp() {
        ApplicationContext context=new ClassPathXmlApplicationContext("AppCon.xml");
      // studentDao= (StudentDao) context.getBean("springjdbcdaoimpl");
       // studentDao= (StudentDao) context.getBean("mydaoimpl");
        studentDao= (StudentDao) context.getBean("studentDao");
    }

    @Test
    public void test1() {
        try {
            System.out.println(studentDao.findStudentbyid(10));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test2() {
        try {
            for (Student e : studentDao.findAllStudent()) {
                System.out.println(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test3() {
        try {
            System.out.println(studentDao.findStudentNamebyid(12));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test4() {
        try {
            System.out.println(studentDao.addStudent(new Student("gaf",135,31,"male",4)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test5() {
        try {
            Student student=new Student("aa",112,20,"male",1);
            student.setId(1);
            System.out.println(studentDao.updateStudent(student));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void test6() {
        Student student=new Student();
        student.setId(2);
        try {
            System.out.println(studentDao.deleteStudent(student));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
