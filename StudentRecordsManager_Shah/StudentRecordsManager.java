import java.io.*;
import java.util.*;

/**
 * Main class that manages student records.
 * This class demonstrates file I/O and exception handling in Java.
 *
 * The StudentRecordsManager handles:
 * - Reading student data from CSV files
 * - Processing and validating the data
 * - Calculating statistics
 * - Writing formatted results to output files
 * - Proper exception handling throughout the process
 */
public class StudentRecordsManager {

    /**
     * Main method to run the program.
     * Handles user input and delegates processing to other methods.
     *
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        StudentRecordsManager manager = new StudentRecordsManager();
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter input filename: ");
        String inputFile = scanner.nextLine();

        System.out.print("Enter output filename: ");
        String outputFile = scanner.nextLine();

        try {
            manager.processStudentRecords(inputFile, outputFile);
            /**
             * TODO: Call the processStudentRecords method with appropriate parameters
             *
             * This should pass the inputFile and outputFile variables to the method
             *
             *
             */
        } catch (Exception e) {
            /**
             * TODO: Handle general exceptions
             *
             * - Display a user-friendly error message
             * - Include the exception's message for debugging purposes
             * - Consider using System.err instead of System.out for error messages
             */
            System.err.println("Something went wrong IDIOTT!! \nYour error is: " + e.getMessage());

        } finally {
            /**
             * The scanner is closed in the finally block to ensure resources are
             * properly released regardless of whether an exception occurred.
             * This demonstrates proper resource management.
             */
            scanner.close();
        }
    }

    /**
     * Process student records from an input file and write results to an output file.
     * This method orchestrates the entire data processing workflow.
     *
     * @param inputFile  Path to the input file containing student records
     * @param outputFile Path to the output file where results will be written
     */
    public void processStudentRecords(String inputFile, String outputFile) {
        try {
            readStudentRecords(inputFile);
            List<Student> students = readStudentRecords(inputFile);
            writeResultsToFile(students, outputFile);
            System.out.println("Success");
            /**
             * TODO: Call readStudentRecords and writeResultsToFile methods
             *
             * 1. Call readStudentRecords to get a list of Student objects
             * 2. Call writeResultsToFile to output the processed data
             * 3. Print a success message to the console
             */
        } catch (FileNotFoundException e) {
            /**
             * TODO: Handle file not found exception
             *
             * Provide a clear message indicating which file couldn't be found
             * and possibly suggest solutions (check spelling, path, etc.)
             */
            System.err.println("Error: " + e.getMessage());
            System.err.println("check file path or spelling");
        } catch (IOException e) {
            /**
             * TODO: Handle general I/O exceptions
             *
             * These could be permission issues, disk full, etc.
             * Provide helpful information about the nature of the I/O problem.
             */
            System.err.println("error: " + e.getMessage());
            System.err.println("maybe check your disk space or permissions");
        }
    }

    /**
     * Read student records from a file and convert them to Student objects.
     * This method demonstrates file reading operations and exception handling.
     *
     * @param filename Path to the input file
     * @return List of Student objects created from the file data
     * @throws IOException If an I/O error occurs during file reading
     */
    public List<Student> readStudentRecords(String filename) throws IOException {
        List<Student> students = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;

            while ((line = bufferedReader.readLine()) != null) {
                lineNumber++;

                try {
                    String[] parts = line.split(",");
                    if (parts.length < 6) {
                        throw new ArrayIndexOutOfBoundsException("Not enough fields");
                    }

                    String studentId = parts[0].trim();
                    String name = parts[1].trim();
                    int[] grades = new int[4];

                    for (int i = 0; i < 4; i++) {
                        grades[i] = Integer.parseInt(parts[2 + i].trim());

                        if (grades[i] < 0 || grades[i] > 100) {
                            throw new InvalidGradeException("Grade out of range: " + grades[i]);
                        }
                    }

                    Student student = new Student(studentId, name, grades);
                    students.add(student);

                } catch (NumberFormatException e) {
                    System.err.println("Error on line " + lineNumber + ": Invalid number format.");
                } catch (InvalidGradeException e) {
                    System.err.println("Error on line " + lineNumber + ": " + e.getMessage());
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.err.println("Error on line " + lineNumber + ": " + e.getMessage());
                }
            }
        }

        return students;
    }

    /**
     * Write processed student results to an output file.
     * This method demonstrates file writing operations.
     *
     * @param students List of Student objects to be written to the file
     * @param filename Path to the output file
     * @throws IOException If an I/O error occurs during file writing
     */
    public void writeResultsToFile(List<Student> students, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            // 1. Write header section
            writer.println("Student Grade Report");
            writer.println("====================");

            // 2. Initialize statistics
            int totalStudents = students.size();
            int sumOfAverages = 0;
            int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;

            // 3. Write each student's info and calculate stats
            for (Student student : students) {
                writer.printf("ID: %s, Name: %s, Average Grade: %.2f, Letter Grade: %s%n",
                        student.getId(),
                        student.getName(),
                        student.getAverageGrade(),
                        student.getLetterGrade());

                double avg = student.getAverageGrade();
                sumOfAverages += avg;

                switch (student.getLetterGrade()) {
                    case "A":
                        countA++;
                        break;
                    case "B":
                        countB++;
                        break;
                    case "C":
                        countC++;
                        break;
                    case "D":
                        countD++;
                        break;
                    case "F":
                        countF++;
                        break;
                }
            }

            // 4. Calculate class average
            double classAverage = totalStudents > 0 ? (double) sumOfAverages / totalStudents : 0.0;

            // 5. Write statistics
            writer.println("\nClass Statistics");
            writer.println("----------------");
            writer.printf("Total Students: %d%n", totalStudents);
            writer.printf("Class Average: %.2f%n", classAverage);
            writer.printf("Grade Distribution:%n");
            writer.printf("A: %d, B: %d, C: %d, D: %d, F: %d%n", countA, countB, countC, countD, countF);
        }
    }
}