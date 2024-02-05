package quizzey.quizzey.Quiz;
import java.util.ArrayList;


public class Quiz {

    private String quizID;
    private String quizName;
    private short quizTimer;
    private String quizEntryCode;
    private short numberOfQuestions;
    private ArrayList<Question> questions;

    // Constructor
    public Quiz(String quizID, String quizName, short quizTimer, String quizEntryCode,
                short numberOfQuestions, ArrayList<Question> questions) {
        this.quizID = quizID;
        this.quizName = quizName;
        this.quizTimer = quizTimer;
        this.quizEntryCode = quizEntryCode.toUpperCase();
        this.numberOfQuestions = numberOfQuestions;
        this.questions = questions;
    }

    public Quiz() {

    }

    // Getters and Setters

    public String getQuizID() {
        return quizID;
    }

    public void setQuizID(String quizID) {
        this.quizID = quizID;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public short getQuizTimer() {
        return quizTimer;
    }

    public void setQuizTimer(short quizTimer) {
        this.quizTimer = quizTimer;
    }

    public String getQuizEntryCode() {
        return quizEntryCode;
    }

    public void setQuizEntryCode(String quizEntryCode) {
        this.quizEntryCode = quizEntryCode.toUpperCase();
    }

    public short getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public void setNumberOfQuestions(short numberOfQuestions) {
        this.numberOfQuestions = numberOfQuestions;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<Question> questions) {
        this.questions = questions;
    }
}
