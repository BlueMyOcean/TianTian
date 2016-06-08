package com.jinjiang.computer.tianyi.entity;

/**
 * Created by Ben on 2016/4/10 0010.
 */

public class Student {
    private int studentId;
    private String studentName;
    private Integer sex;
    private String className;
    private Integer absent;
    private Integer late;
    private Integer ill;

    public Integer getIll() {
        return ill;
    }

    public void setIll(Integer ill) {
        this.ill = ill;
    }

    private Integer total;

    public Student() {

    }

    public Student(String studentName, int studentId, Integer sex, String className, Integer absent, Integer late, Integer total, Integer ill) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.sex = sex;
        this.className = className;
        this.absent = absent;
        this.late = late;
        this.total = total;
        this.ill = ill;
    }

    public Student(Student student) {
        this.studentName = student.getStudentName();
        this.studentId = student.getStudentId();
        this.sex = student.getSex();
        this.className = student.getClassName();
        this.absent = student.getAbsent();
        this.late = student.getLate();
        this.total = student.getTotal();
        this.ill = student.getIll();
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public Integer getAbsent() {
        return absent;
    }

    public void setAbsent(Integer absent) {
        this.absent = absent;
    }

    public Integer getLate() {
        return late;
    }

    public void setLate(Integer late) {
        this.late = late;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        if (studentId != student.studentId) return false;
        if (studentName != null ? !studentName.equals(student.studentName) : student.studentName != null)
            return false;
        if (sex != null ? !sex.equals(student.sex) : student.sex != null) return false;
        if (className != null ? !className.equals(student.className) : student.className != null)
            return false;
        if (absent != null ? !absent.equals(student.absent) : student.absent != null) return false;
        if (late != null ? !late.equals(student.late) : student.late != null) return false;
        if (total != null ? !total.equals(student.total) : student.total != null) return false;
        if (ill != null ? !ill.equals(student.ill) : student.ill != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + (studentName != null ? studentName.hashCode() : 0);
        result = 31 * result + (sex != null ? sex.hashCode() : 0);
        result = 31 * result + (className != null ? className.hashCode() : 0);
        result = 31 * result + (absent != null ? absent.hashCode() : 0);
        result = 31 * result + (late != null ? late.hashCode() : 0);
        result = 31 * result + (total != null ? total.hashCode() : 0);
        result = 31 * result + (ill != null ? ill.hashCode() : 0);
        return result;
    }
}
