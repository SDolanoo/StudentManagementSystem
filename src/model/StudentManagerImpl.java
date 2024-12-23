package model;

import java.sql.*;
import java.util.ArrayList;

public class StudentManagerImpl implements StudentManager {

    private static final String DB_URL = "jdbc:sqlite:students.db";

    public StudentManagerImpl() {
        createTableIfNotExists();
    }

    // Nawiązywanie połączenia z bazą danych
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void createTableIfNotExists() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS students ("
                + "studentID TEXT PRIMARY KEY,"
                + "name TEXT NOT NULL,"
                + "age INTEGER NOT NULL,"
                + "grade REAL NOT NULL"
                + ");";

        try (Connection connection = connect();
             Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    @Override
    public void addStudent(Student student) {

        String insertSQL = "INSERT INTO students (studentID, name, age, grade) VALUES (?, ?, ?, ?);";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, student.getStudentID());
            preparedStatement.setString(2, student.getName());
            preparedStatement.setInt(3, student.getAge());
            preparedStatement.setDouble(4, student.getGrade());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage());
        }
    }

    @Override
    public void removeStudent(String studentID) {
        String deleteSQL = "DELETE FROM students WHERE studentID = ?;";

        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.setString(1, studentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error removing student: " + e.getMessage());
        }
    }

    @Override
    public boolean doesStudentExist(String studentID) {
        String querySQL = "SELECT 1 FROM students WHERE studentID = ?;";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, studentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            System.err.println("Error checking if student exists: " + e.getMessage());
        }

        return false;
    }

    @Override
    public ArrayList<Object> getStudentById(String studentID) {
        ArrayList<Object> studentData = new ArrayList<>();
        String querySQL = "SELECT studentID, name, age, grade FROM students WHERE studentID = ?;";

        try (Connection connection = DriverManager.getConnection(DB_URL);
             PreparedStatement preparedStatement = connection.prepareStatement(querySQL)) {
            preparedStatement.setString(1, studentID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    studentData.add(resultSet.getString("studentID"));
                    studentData.add(resultSet.getString("name"));
                    studentData.add(resultSet.getInt("age"));
                    studentData.add(resultSet.getDouble("grade"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Error fetching student by ID: " + e.getMessage());
        }

        return studentData;
    }


    @Override
    public void updateStudent(String name, int age, double grade, String studentID) {
        String updateSQL = "UPDATE students SET name = ?, age = ?, grade = ? WHERE studentID = ?;";

        // Example update logic - replace with dynamic inputs or UI integration
        try (Connection connection = connect();
             PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            // Dummy values for example purposes
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, age);
            preparedStatement.setDouble(3, grade);
            preparedStatement.setString(4, studentID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating student: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Student> displayAllStudents() {
        ArrayList<Student> students = new ArrayList<>();
        String selectSQL = "SELECT * FROM students;";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectSQL)) {

            while (resultSet.next()) {
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

        return students;
    }

    @Override
    public double calculateAverageGrade() {
        String avgSQL = "SELECT AVG(grade) AS averageGrade FROM students;";

        try (Connection connection = connect();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(avgSQL)) {

            if (resultSet.next()) {
                return resultSet.getDouble("averageGrade");
            }

        } catch (SQLException e) {
            System.err.println("Error calculating average grade: " + e.getMessage());
        }

        return 0.0;
    }
}
