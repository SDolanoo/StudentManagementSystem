package controller;

import model.Student;
import model.StudentManagerImpl;

import java.sql.SQLException;
import java.util.ArrayList;

public class StudentControllerImpl implements StudentController {

    private StudentManagerImpl studentManager;

    public StudentControllerImpl() {
        this.studentManager = new StudentManagerImpl();
    }


    @Override
    public String addStudent(String name, String age, String grade, String studentID) {
        try {
            if (studentManager.doesStudentExist(studentID)) {
                return "Student with id: " + studentID + " already exists";
            }

            int newAge = Integer.parseInt(age);
            double newGrade = Double.parseDouble(grade);

            if (studentID.isEmpty()) throw new IllegalArgumentException("ID cannot be empty.");
            if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
            if (newAge <= 0 || newAge > 116) throw new IllegalArgumentException("Age must be positive.");
            if (newGrade < 0.0 || newGrade > 100.0) throw new IllegalArgumentException("Grade must be between 0.0 and 100.0.");
            Student student = new Student(name, newAge, newGrade, studentID);
            studentManager.addStudent(student);
            return "Student added successfully";
        } catch (NumberFormatException ex) {
            return "Error: Age and Grade must be numeric.\n";
        } catch (IllegalArgumentException ex) {
            return ex + "\n";
        }
    }

    @Override
    public String removeStudent(String studentID) {
        if (!studentManager.doesStudentExist(studentID)) {
            return "Student with id: " + studentID + " does not exist";
        }
        try {
            studentManager.removeStudent(studentID);
            return "Student removed successfully";
        } catch (Exception e) {
            return "Error " + e;
        }
    }

    @Override
    public String updateStudent(String name, String age, String grade, String studentID) {
        try {
            if (studentID.isEmpty()) {
                throw new IllegalArgumentException("ID cannot be empty.");
            }
            if (!studentManager.doesStudentExist(studentID)) {
                return "Student with id: " + studentID + " does not exist";
            }

            ArrayList<Object> studentData = studentManager.getStudentById(studentID);
                // studentData == [ StudentID, name, age, grade ]
            String baseName = (String) studentData.get(1);
            int baseAge = (Integer) studentData.get(2);
            double baseGrade = (Double) studentData.get(3);

            if (!name.isEmpty()) { // if name is not empty
                baseName = name;
            }

            if (!age.isEmpty()) { // if age is not empty
                // if age is number, then catch NumberFormatException
                baseAge = Integer.parseInt(age); // set age
            }
            if (!grade.isEmpty()) { // if grade is not empty
                // if grade is double, then catch NumberFormatException
                baseGrade = Double.parseDouble(grade); // set grade
            }
            // after validation complete, update student in database
            studentManager.updateStudent(baseName, baseAge, baseGrade, studentID);
            return String.format("Student updated, name: %s, age: %d, grade: %f", baseName, baseAge, baseGrade);
        } catch (NumberFormatException ex) {
            return "Error: Age and Grade must be numeric.\n";
        }
    }

    @Override
    public ArrayList<Student> displayAllStudents() {
        ArrayList<Student> students = studentManager.displayAllStudents();
        return students;
    }

    @Override
    public double calculateAverageGrade() {
        return studentManager.calculateAverageGrade();
    }

}
