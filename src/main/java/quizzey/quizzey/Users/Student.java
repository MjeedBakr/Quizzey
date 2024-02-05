package quizzey.quizzey.Users;

import quizzey.quizzey.Quiz.Quiz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Student extends User{

    //<QuizID, numberOFTrueAnswers>
    private HashMap<String, Double> attemptedQuizzesList = new HashMap<>();

    public static ArrayList<Student> studentsList = new ArrayList<Student>();

    public Student(String personID, String firstName, String lastName, String email, String password, Role personRole) {
        super(personID, firstName, lastName, email, password, personRole);
    }

    public static void addStudentToList(Student student) {
        studentsList.add(student);
    }

    public static Student getStudentByEmailAndPassword(String email, String password)
    {
        for (Student stu: studentsList)
        {
            if (Objects.equals(stu.getPassword(), password) && Objects.equals(stu.getEmail(), email))
                return stu;
        }
        return null;
    }

    public static Student getStudentByID(String ID)
    {
        for (Student stu: studentsList)
        {
            if (Objects.equals(stu.getPersonID(), ID))
                return stu;
        }
        return null;
    }

    // Constructor 1 - Default Constructor
    public Student() {
        this.attemptedQuizzesList = new HashMap<>();
    }

    // Constructor 2 - Parameterized Constructor
    public Student(String personID, String firstName, String lastName, String email, String password, HashMap<String, Double> attemptedQuizzesList) {
        super(personID, firstName, lastName, email, password, Role.STUDENT);
        this.attemptedQuizzesList = attemptedQuizzesList;
    }

    // Getter and Setter for studentGrade
    public HashMap<String, Double> getStudentGrade() {
        return attemptedQuizzesList;
    }

    public void setStudentGrade(HashMap<String, Double> studentGrade) {
        this.attemptedQuizzesList = studentGrade;
    }

    // Method to add a grade for a quiz
    public void addGrade(String quizID, Double grade) {
        this.attemptedQuizzesList.put(quizID, grade);

    }


    public static void addGradeForStudent(Student student, Double grade, String quizID) {
        student.addGrade(quizID, grade);
    }

    // Method to get a grade for a quiz
    public Double getGrade(String quizID) {
        return attemptedQuizzesList.get(quizID);
    }


}
