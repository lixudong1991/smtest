package my.jdbc.bean;

import java.util.Date;

/**
 * Created by Tomcat on 2017/9/16.
 */
public class Student {
    private int id;
    private String name;
    private int number;
    private int age;
    private String sex;
    private int classid;
    private Date birthday;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getClassid() {
        return classid;
    }

    public void setClassid(int classid) {
        this.classid = classid;
    }

    public Student() {
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Student(String name, int number, int age, String sex, int classid) {
        this.name = name;
        this.number = number;
        this.age = age;
        this.sex = sex;
        this.classid = classid;
        this.birthday=new Date();
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", classid=" + classid +
                ", birthday=" + birthday +
                '}';
    }
}
