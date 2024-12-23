package model;

import java.util.ArrayList;

public interface StudentManager {

    // Dodaje nowego studenta do bazy danych
    String addStudent(Student student);

    // Usuwa studenta z bazy danych za pomocą unikalnego ID
    String removeStudent(String studentID);

    // Aktualizuje dane istniejącego studenta
    String updateStudent(String name, int age, double grade, String studentID);

    // Pobiera i zwraca listę wszystkich studentów
    ArrayList<Student> displayAllStudents();

    // Oblicza i zwraca średnią ocen wszystkich studentów
    double calculateAverageGrade();
}