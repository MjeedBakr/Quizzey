package quizzey.quizzey.Quiz;

import java.util.HashMap;

import java.util.HashMap;

public class Question {

    private String questionID;
    private String theQuestion;

    // Holds <choiceNumber, isCorrect>
    private HashMap<String, Boolean> choices;

    private short questionAnsweringTime;

    // Constructor
    public Question(String questionID, String theQuestion, HashMap<String, Boolean> choices, short questionAnsweringTime) {
        this.questionID = questionID;
        this.theQuestion = theQuestion;
        this.choices = choices;
        this.questionAnsweringTime = questionAnsweringTime;
    }

    // Getters and Setters

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID;
    }

    public String getTheQuestion() {
        return theQuestion;
    }

    public void setTheQuestion(String theQuestion) {
        this.theQuestion = theQuestion;
    }

    public HashMap<String, Boolean> getChoices() {
        return choices;
    }

    public void setChoices(HashMap<String, Boolean> choices) {
        this.choices = choices;
    }

    public short getQuestionAnsweringTime() {
        return questionAnsweringTime;
    }

    public void setQuestionAnsweringTime(short questionAnsweringTime) {
        this.questionAnsweringTime = questionAnsweringTime;
    }
}

