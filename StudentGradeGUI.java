import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Student {
    String name;
    double grade;

    Student(String name, double grade) {
        this.name = name;
        this.grade = grade;
    }
}

public class StudentGradeGUI extends JFrame {
    private JTextField nameField, gradeField;
    private JTextArea reportArea;
    private ArrayList<Student> students = new ArrayList<>();

    public StudentGradeGUI() {
        setTitle("Student Grade Manager");
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Student Details"));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grade:"));
        gradeField = new JTextField();
        inputPanel.add(gradeField);

        JButton addButton = new JButton("Add Student");
        JButton showReportButton = new JButton("Show Report");
        inputPanel.add(addButton);
        inputPanel.add(showReportButton);

        add(inputPanel, BorderLayout.NORTH);

        // Report area
        reportArea = new JTextArea();
        reportArea.setEditable(false);
        reportArea.setBorder(BorderFactory.createTitledBorder("Added Students"));
        add(new JScrollPane(reportArea), BorderLayout.CENTER);

        // Add button action
        addButton.addActionListener(_ -> addStudent());

        // Show report button action
        showReportButton.addActionListener(_ -> showReport());

    }

    private void addStudent() {
        String name = nameField.getText().trim();
        String gradeText = gradeField.getText().trim();

        if (name.isEmpty() || gradeText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both name and grade.");
            return;
        }

        try {
            double grade = Double.parseDouble(gradeText);
            students.add(new Student(name, grade));
            reportArea.append("✔ " + name + " - " + grade + "\n");

            // Clear fields
            nameField.setText("");
            gradeField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid grade. Please enter a number.");
        }
    }

    private void showReport() {
        if (students.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No students added yet.");
            return;
        }

        double total = 0;
        double highest = Double.MIN_VALUE;
        double lowest = Double.MAX_VALUE;
        String topStudent = "", lowStudent = "";

        for (Student s : students) {
            total += s.grade;
            if (s.grade > highest) {
                highest = s.grade;
                topStudent = s.name;
            }
            if (s.grade < lowest) {
                lowest = s.grade;
                lowStudent = s.name;
            }
        }

        double average = total / students.size();

        StringBuilder report = new StringBuilder();
        report.append("===== Student Summary Report =====\n");
        for (Student s : students) {
            report.append("Name: ").append(s.name).append(", Grade: ").append(s.grade).append("\n");
        }

        report.append("\nAverage Grade: ").append(String.format("%.2f", average));
        report.append("\nHighest Grade: ").append(highest).append(" (").append(topStudent).append(")");
        report.append("\nLowest Grade: ").append(lowest).append(" (").append(lowStudent).append(")");

        JOptionPane.showMessageDialog(this, report.toString(), "Summary Report", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            StudentGradeGUI frame = new StudentGradeGUI();
            frame.setVisible(true);
        });
    }
}