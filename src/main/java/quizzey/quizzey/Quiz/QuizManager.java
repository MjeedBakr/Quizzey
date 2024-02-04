package quizzey.quizzey.Quiz;

import java.util.ArrayList;

public class QuizManager {

    public static ArrayList<Quiz> quizzesList = new ArrayList<>();



    public static void displayQuizzes() {
        for (Quiz quiz : quizzesList) {
            System.out.println("Quiz ID: " + quiz.getQuizID());
            System.out.println("Quiz Name: " + quiz.getQuizName());
            System.out.println("Taking Time: " + quiz.getQuizTimer() + " minutes");
            System.out.println("Entry Code: " + quiz.getQuizEntryCode());
            System.out.println("Number of Questions: " + quiz.getNumberOfQuestions());

            System.out.println("Questions:");
            for (Question question : quiz.getQuestions()) {
                System.out.println("  Question ID: " + question.getQuestionID());
                System.out.println("  Question: " + question.getTheQuestion());
                System.out.println("  Choices: " + question.getChoices());
            }

            System.out.println("-------------------------------");
        }
    }
}
