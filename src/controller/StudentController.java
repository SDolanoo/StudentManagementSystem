package controller;

import model.Student;

import java.util.ArrayList;

public interface StudentController {

    // Dodaje nowego studenta do bazy danych
    String addStudent(String name, String age, String grade, String studentID);

    // Usuwa studenta z bazy danych za pomocą unikalnego ID
    String removeStudent(String studentID);

    // Aktualizuje dane istniejącego studenta
    String updateStudent(String name, String age, String grade, String studentID);

    // Pobiera i zwraca listę wszystkich studentów
    ArrayList<Student> displayAllStudents();

    // Oblicza i zwraca średnią ocen wszystkich studentów
    double calculateAverageGrade();
}
