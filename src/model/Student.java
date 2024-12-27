package model;

public class Student {

    // Atrybuty
    private String name;
    private int age;
    private double grade;
    private String studentID;

    // Konstruktor
    public Student(String name, int age, double grade, String studentID) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be positive.");
        }
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0.");
        }
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.studentID = studentID;
    }

    // Gettery i settery
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be positive.");
        }
        this.age = age;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException("Grade must be between 0.0 and 100.0.");
        }
        this.grade = grade;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    // Metoda displayInfo()
    public void displayInfo() {
        System.out.println("model.Student Info:");
        System.out.println("ID: " + studentID);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Grade: " + grade);
    }

}

