package model;

import java.util.ArrayList;

public interface StudentManager {

    // Dodaje nowego studenta do bazy danych
    void addStudent(Student student);

    // Usuwa studenta z bazy danych za pomocą unikalnego ID
    void removeStudent(String studentID);

    // sprawdza czy użytkownik istnieje
    boolean doesStudentExist(String studentID);

    // znajduje i oddaje dane studenta
    ArrayList<Object> getStudentById(String studentID);

    // Aktualizuje dane istniejącego studenta
    void updateStudent(String name, int age, double grade, String studentID);

    // Pobiera i zwraca listę wszystkich studentów
    ArrayList<Student> displayAllStudents();

    // Oblicza i zwraca średnią ocen wszystkich studentów
    double calculateAverageGrade();
}