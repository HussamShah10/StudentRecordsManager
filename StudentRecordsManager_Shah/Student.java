/**
 * Class representing a student record with grades.
 * This class demonstrates basic OOP principles and data encapsulation.
 */
public class Student {
    private String studentId;
    private String name;
    private int[] grades;
    private double averageGrade;
    private char letterGrade;

    /**
     * Constructor to initialize a Student object
     * @param studentId The student's ID
     * @param name The student's name
     * @param grades Array of the student's grades
     */
    public Student(String studentId, String name, int[] grades) {
        this.studentId = studentId;
        this.name = name;
        this.grades = grades.clone(); // Clone to protect encapsulation
        this.averageGrade = calculateAverage();
        this.letterGrade = determineLetterGrade();
    }

    /**
     * Calculates the average of all grades
     * @return The average grade as a double
     */
    private double calculateAverage() {
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return grades.length > 0 ? (double) sum / grades.length : 0.0;
    }

    /**
     * Determines the letter grade based on the average
     * @return A character representing the letter grade (A, B, C, D, or F)
     */
    private char determineLetterGrade() {
        if (averageGrade >= 90) return 'A';
        else if (averageGrade >= 80) return 'B';
        else if (averageGrade >= 70) return 'C';
        else if (averageGrade >= 60) return 'D';
        else return 'F';
    }

    // Getters
    public String getId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public int[] getGrades() {
        return grades.clone(); // Clone to maintain encapsulation
    }

    public double getAverageGrade() {
        return averageGrade;
    }

    public String getLetterGrade() {
        return String.valueOf(letterGrade);
    }

    /**
     * Returns a string representation of the student
     * @return String containing all student information
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ID: ").append(studentId)
                .append(", Name: ").append(name)
                .append(", Grades: ");

        for (int grade : grades) {
            sb.append(grade).append(" ");
        }

        sb.append(String.format(", Average: %.2f", averageGrade))
                .append(", Letter Grade: ").append(letterGrade);

        return sb.toString();
    }
}
