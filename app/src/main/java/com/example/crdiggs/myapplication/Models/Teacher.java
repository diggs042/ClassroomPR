package com.example.crdiggs.myapplication.Models;

import java.io.Serializable;

public class Teacher implements Serializable {

    private String teacherName;
    private String school;
    private int grade;
    private int studentCount;
    private boolean isAdmin;


    public Teacher(){
        // Empty Constructor for Firebase Implementation
    }

    public Teacher(String teacherName){
        this.teacherName = teacherName;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getGrade() {
        return grade;
    }

    public void setStudentCount(int studentCount) {
        this.studentCount = studentCount;
    }

    public int getStudentCount() {
        return studentCount;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

}
