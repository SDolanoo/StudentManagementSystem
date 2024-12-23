package controller;

import model.Student;
import model.StudentManagerImpl;

import java.util.ArrayList;

public class StudentControllerImpl implements StudentController {

    private StudentManagerImpl studentManager;

    @Override
    public String addStudent(String name, int age, double grade, String studentID) {
        return "";
    }

    @Override
    public String removeStudent(String studentID) {
        return "";
    }

    @Override
    public String updateStudent(String name, int age, double grade, String studentID) {
        return "";
    }

//    @Override
//    public String updateStudent(String name, String age, String grade, String studentID) {
//        if (studentID.isEmpty()) {
//            throw new IllegalArgumentException("ID cannot be empty.");
//        }
//        return name;
//    }

    @Override
    public ArrayList<Student> displayAllStudents() {
        return null;
    }

    @Override
    public double calculateAverageGrade() {
        return 0;
    }

}
