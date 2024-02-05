package quizzey.quizzey.Quiz;

import java.util.HashMap;

import java.util.HashMap;
import java.util.Map;

public class Question {

    private String questionID;
    private String theQuestion;

    // Holds <choiceNumber, isCorrect>
    private HashMap<String, Boolean> choices;


    // Constructor
    public Question(String theQuestion, HashMap<String, Boolean> choices) {
        this.theQuestion = theQuestion;
        this.choices = choices;
    }

    public Question() {

    }

    // Getters and Setters

    public String getQuestionID() {
        return questionID;
    }

    public void setQuestionID(String questionID) {
        this.questionID = questionID.toUpperCase();
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

    public String getChoiceAtIndex(int index) {
        int currentIndex = 0;
        for (Map.Entry<String, Boolean> entry : choices.entrySet()) {
            if (currentIndex == index) {
                return entry.getKey();
            }
            currentIndex++;
        }
        return null; // Index out of bounds
    }

    public void updateChoice(String oldChoice, String newChoice, boolean isCorrect) {
        // Check if the old choice exists
        if (choices.containsKey(oldChoice)) {
            // Get the current correctness value of the old choice
            boolean oldIsCorrect = choices.get(oldChoice);

            // Remove the old choice
            choices.remove(oldChoice);

            // Add the new choice with the specified correctness value
            choices.put(newChoice, isCorrect);

            // Update other choices if needed to maintain the correctness value consistency
            if (isCorrect) {
                for (Map.Entry<String, Boolean> entry : choices.entrySet()) {
                    String currentChoice = entry.getKey();
                    if (!currentChoice.equals(newChoice) && entry.getValue() == isCorrect) {
                        choices.put(currentChoice, !isCorrect);
                    }
                }
            } else {
                // If the new choice is marked as incorrect, update other choices to ensure only one is correct
                for (Map.Entry<String, Boolean> entry : choices.entrySet()) {
                    String currentChoice = entry.getKey();
                    if (!currentChoice.equals(newChoice) && entry.getValue() == isCorrect) {
                        choices.put(currentChoice, !isCorrect);
                    }
                }
                // Update the correctness value of the old choice to the original value
                choices.put(oldChoice, oldIsCorrect);
            }
        }
    }

}

