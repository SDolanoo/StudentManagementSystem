package view;

import controller.StudentControllerImpl;
import model.Student;
import controller.StudentController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class StudentManagementGUI {

    private final StudentController studentController;

    public StudentManagementGUI() {
        studentController = new StudentControllerImpl();
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        // Główne okno
        JFrame frame = new JFrame("Student Management System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);

        // Panel wejściowy
            JPanel inputPanel = new JPanel(new GridLayout(5, 2));

        JLabel idLabel = new JLabel("Student ID:");
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
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            String age = ageField.getText().trim();
            String grade = gradeField.getText().trim();

            String message = studentController.addStudent(name, age, grade, id);
            outputArea.append(message + "\n");
        });

        removeButton.addActionListener(e ->     {
            String id = idField.getText().trim();
            String removeMessage = studentController.removeStudent(id);
            outputArea.append(removeMessage + "\n");
        });

        updateButton.addActionListener(e -> {
            String studentID = idField.getText().trim(); // studentID can not be empty!!
            String name = nameField.getText().trim();
            String age = ageField.getText().trim();
            String grade = gradeField.getText().trim();

            String message = studentController.updateStudent(name, age, grade, studentID);
            outputArea.append(message + "\n");

        });

        displayButton.addActionListener(e -> {
            ArrayList<Student> students = studentController.displayAllStudents();
            outputArea.append("All Students:\n");
            for (Student student : students) {
                outputArea.append(student.getStudentID() + ": " + student.getName() + ", Age: " + student.getAge() + ", Grade: " + student.getGrade() + "\n");
            }
        });

        averageButton.addActionListener(e -> {
            double average = studentController.calculateAverageGrade();
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
