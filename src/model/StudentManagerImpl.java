package model;

import java.sql.*;
import java.util.ArrayList;

public class StudentManagerImpl implements StudentManager {

    // Database URL pointing to the SQLite database file
    private static final String DB_URL = "jdbc:sqlite:students.db";

    // Constructor to initialize the database and ensure the table exists
    public StudentManagerImpl() {
        createTableIfNotExists();
    }

    // Establishes a connection to the SQLite database
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    // Creates the "students" table in the database if it doesn't already exist
    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students ("
                + "studentID TEXT PRIMARY KEY," // Kolumna studentID jako klucz główny
                + "name TEXT NOT NULL,"         // Kolumna name (nie może być pusta)
                + "age INTEGER NOT NULL,"       // Kolumna age (nie może być pusta)
                + "grade REAL NOT NULL"         // Kolumna grade (nie może być pusta)
                + ");";

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL); // Executes the SQL statement to create the table
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    // Adds a new student to the database
    @Override
    public void addStudent(Student student) {
        String insertSQL = "INSERT INTO students (studentID, name, age, grade) VALUES (?, ?, ?, ?);";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            // Sets the parameters for the SQL query
            preparedStatement.setString(1, student.getStudentID());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setDouble(4, student.getGrade());
            preparedStatement.executeUpdate(); /// Executes the query to insert the student
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    // Removes a student from the database using their student ID
    @Override
    public void removeStudent(String studentID) {
        String deleteSQL = "DELETE FROM students WHERE studentID = ?;";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, studentID); // Sets the student ID parameter
            preparedStatement.executeUpdate(); // Executes the delete query
        } catch (SQLException e) {
            System.err.println("Error removing student: " + e.getMessage());
        }
    }

    // Checks if a student exists in the database using their student ID
    @Override
    public boolean doesStudentExist(String studentID) {
        String querySQL = "SELECT 1 FROM students WHERE studentID = ?;";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            // Sets the student ID parameter
            preparedStatement.setString(1, studentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // returns true if student exists
            }
        } catch (SQLException e) {
            System.err.println("Error checking if student exists: " + e.getMessage());
        }

        return false;
    }

    // Retrieves a student's details from the database by their student ID
    @Override
    public Student getStudentById(String studentID) {
        String querySQL = "SELECT studentID, name, age, grade FROM students WHERE studentID = ?;";
        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, studentID); // Sets the student ID parameter
            // Executes the query and processes the result
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    // Extracts student details from the result set
                    String studentName = resultSet.getString("name");
                    int studentAge = resultSet.getInt("age");
                    double studentGrade = resultSet.getDouble("grade");
                    // Creates a new Student object with the retrieved data
                    Student student = new Student(studentName, studentAge, studentGrade, studentID);
                    return student;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student by ID: " + e.getMessage());
        }
        return null; // Returns null if the student does not exist
    }

    // Updates a student's details in the database
    @Override
    public void updateStudent(String name, int age, double grade, String studentID) {
        String updateSQL = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?;";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            // Sets the parameters for the update query
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setDouble(3, grade);
            preparedStatement.setString(4, studentID);
            preparedStatement.executeUpdate();  // Executes the update query
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }

    // Retrieves a list of all students from the database
    @Override
    public ArrayList<Student> displayAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String selectSQL = "SELECT * FROM students;";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
                // Iterates through the result set and adds each student to the list
                String studentID = resultSet.getString("studentID");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                double grade = resultSet.getDouble("grade");

                Student student = new Student(name, age, grade, studentID);
                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println("Error displaying all students: " + e.getMessage());
        }

        return students; // Returns the list of students
    }

    // Calculates the average grade of all students in the database
    @Override
    public double calculateAverageGrade() {
        String avgSQL = "SELECT AVG(grade) AS averageGrade FROM students;";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(avgSQL)) {
            // Retrieves the average grade from the result set
            if (resultSet.next()) {
                return resultSet.getDouble("averageGrade"); // Returns average grade if no errors
            }

        } catch (SQLException e) {
            System.err.println("Error calculating average grade: " + e.getMessage());
        }

        return 0.0; // Returns 0.0 if there was an error or no data
    }
}
