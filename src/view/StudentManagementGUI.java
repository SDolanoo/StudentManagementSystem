package view;

import model.Student;
import model.StudentManagerImpl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentManagementGUI {

    private StudentManagerImpl studentManager;

    public StudentManagementGUI() {
        studentManager = new StudentManagerImpl();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Główne okno
        JFrame frame = new JFrame("model.Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);

        // Panel wejściowy
            JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        JLabel idLabel = new JLabel("model.Student ID:");
        JTextField idField = new JTextField();

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        JLabel gradeLabel = new JLabel("Grade:");
        JTextField gradeField = new JTextField();

        inputPanel.add(idLabel);
        inputPanel.add(idField);
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(ageLabel);
        inputPanel.add(ageField);
        inputPanel.add(gradeLabel);
        inputPanel.add(gradeField);

        // Panel przycisków
        JPanel buttonPanel = new JPanel(new GridLayout(1, 5));
        buttonPanel.setMaximumSize(new Dimension(100, 50));

        JButton addButton = new JButton("Add model.Student");
        JButton removeButton = new JButton("Remove model.Student");
        JButton updateButton = new JButton("Update model.Student");
        JButton displayButton = new JButton("Display All Students");
        JButton averageButton = new JButton("Calculate Average");

        addButton.setMaximumSize(new Dimension(100, 50));
        removeButton.setMaximumSize(new Dimension(100, 50));
        updateButton.setMaximumSize(new Dimension(100, 50));
        displayButton.setMaximumSize(new Dimension(100, 50));
        averageButton.setMaximumSize(new Dimension(100, 50));

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(averageButton);

        // Panel wyjściowy
        JPanel outputGrid = new JPanel(new GridLayout(1, 1));
        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setPreferredSize(new Dimension(frame.getWidth(), 250));
        outputGrid.add(outputScrollPane);

        // Obsługa zdarzeń
        addButton.addActionListener(e -> {
            try {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                int age = Integer.parseInt(ageField.getText().trim());
                double grade = Double.parseDouble(gradeField.getText().trim());

                if (id.isEmpty()) throw new IllegalArgumentException("ID cannot be empty.");
                if (name.isEmpty()) throw new IllegalArgumentException("Name cannot be empty.");
                if (age <= 0 || age > 116) throw new IllegalArgumentException("Age must be positive.");
                if (grade < 0.0 || grade > 100.0) throw new IllegalArgumentException("Grade must be between 0.0 and 100.0.");

                String message = studentManager.addStudent(new Student(name, age, grade, id));
                outputArea.append(message + "\n");
            } catch (NumberFormatException ex) {
                outputArea.append("Error: Age and Grade must be numeric.\n");
            } catch (IllegalArgumentException ex) {
                outputArea.append(ex + "\n");
            }
        });

        removeButton.addActionListener(e ->     {
            String id = idField.getText().trim();
            String removeMessage = studentManager.removeStudent(id);
            outputArea.append(removeMessage + "\n");
        });

        updateButton.addActionListener(e -> {
            try {
                String studentID = idField.getText().trim(); // studentID can not be empty!!
                if (studentID.isEmpty()) throw new IllegalArgumentException("ID cannot be empty.");

                String name = null;
                int age = -1;
                double grade = -1.0;

                if (!nameField.getText().trim().isEmpty()) { // if name is not empty
                    name = nameField.getText().trim();
                }

                if (!ageField.getText().trim().isEmpty()) { // if age is not empty
                    // if age is number, then catch NumberFormatException
                    age = Integer.parseInt(ageField.getText().trim()); // set age
                }

                if (!gradeField.getText().trim().isEmpty()) { // if grade is not empty
                    // if grade is double, then catch NumberFormatException
                    grade = Double.parseDouble(gradeField.getText().trim()); // set grade
                }

                String message = studentManager.updateStudent(name, age, grade, studentID);
                outputArea.append(message + "\n");
            } catch (NumberFormatException ex) {
                outputArea.append("Error: Age and Grade must be numeric.\n");
            }
        });

        displayButton.addActionListener(e -> {
            ArrayList<Student> students = studentManager.displayAllStudents();
            outputArea.append("All Students:\n");
            for (Student student : students) {
                outputArea.append(student.getStudentID() + ": " + student.getName() + ", Age: " + student.getAge() + ", Grade: " + student.getGrade() + "\n");
            }
        });

        averageButton.addActionListener(e -> {
            double average = studentManager.calculateAverageGrade();
            outputArea.append("Average Grade: " + average + "\n");
        });

        // Układ główny
        frame.setLayout(new BorderLayout());
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(outputGrid, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentManagementGUI::new);
    }
}
