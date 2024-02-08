package quizzey.quizzey;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import quizzey.quizzey.Quiz.Question;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Users.Student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayQuizController {

    @FXML
    public Text txtTimer;
    @FXML
    public Button answerBtn1;
    @FXML
    public Button answerBtn2;
    @FXML
    public Button answerBtn3;
    @FXML
    public Button answerBtn4;
    public static int currentQuestionIndex = 0;
    public int remainingTimeInSeconds;
    public static Student attemptingStudent;
    public static Quiz attemptingQuiz;
    public Text txtNextQuestion;

    public int correctAnswers;
    public int wrongAnswers;
    public Text txtQuestionID;
    public Text txtQuestion;
    public Text txtMessage;
    private Timeline mainTimer;


    public void initialize() {
        mainTimer = createMainTimer();
        mainTimer.play();
        displayCurrentQuestion();

    }

    private Timeline createMainTimer() {
        int totalSeconds = attemptingQuiz.getQuizTimer() * 60; // Convert minutes to seconds
        remainingTimeInSeconds = totalSeconds;

        Timeline timer = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            remainingTimeInSeconds--;
            updateTimerText();
            if (remainingTimeInSeconds <= 0) {
                finishQuizDueToTimeout();
            }
        }));
        timer.setCycleCount(remainingTimeInSeconds);
        return timer;
    }

    private void pauseMainTimer() {
        mainTimer.pause();
    }

    private void resumeMainTimer() {
        mainTimer.play();
    }

    public void updateTimerText() {
        int minutes = remainingTimeInSeconds / 60;
        int seconds = remainingTimeInSeconds % 60;
        txtTimer.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void displayCurrentQuestion() {
        if (currentQuestionIndex != 0) {
            txtMessage.setText("");
            txtNextQuestion.setText("");

        }

        Question currentQuestion = attemptingQuiz.getQuestions().get(currentQuestionIndex);
        txtQuestion.setText(currentQuestion.getTheQuestion());
        txtQuestionID.setText(currentQuestion.getQuestionID());

        ArrayList<Button> answerButtons = new ArrayList<>();
        answerButtons.add(answerBtn1);
        answerButtons.add(answerBtn2);
        answerButtons.add(answerBtn3);
        answerButtons.add(answerBtn4);

        HashMap<String, Boolean> choices = currentQuestion.getChoices();
        int i = 0;
        for (String choice : choices.keySet()) {
            Button button = answerButtons.get(i);
            button.setText(choice);
            button.setOnAction(event -> handleAnswerButtonClick(button, choices.get(choice)));
            i++;
        }
    }

    private void handleAnswerButtonClick(Button button, boolean isCorrect) {

        pauseMainTimer();

        if (isCorrect) {
            // Set button color to green for correct answer
            button.setStyle("-fx-background-color: green");
            txtMessage.setText("Correct :)");
            txtMessage.setStyle("-fx-fill: green");
            correctAnswers++;
        } else {
            // Set button color to red for incorrect answer
            button.setStyle("-fx-background-color: red");
            txtMessage.setText("Incorrect :(");
            txtMessage.setStyle("-fx-fill: red");
            wrongAnswers++;
        }

        disableAnswerButtons();

        // Countdown to the next question
        AtomicInteger countDownSeconds = new AtomicInteger(3);
        txtNextQuestion.setText("Next Question in " + countDownSeconds.get() + "...");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            countDownSeconds.getAndDecrement();
            txtNextQuestion.setText("Next Question in " + countDownSeconds.get() + "...");
            if (countDownSeconds.get() == 0) {
                currentQuestionIndex++;
                if (currentQuestionIndex < attemptingQuiz.getQuestions().size()) {
                    resumeMainTimer();
                    displayCurrentQuestion();
                } else {
                    finishQuiz();
                }
            }
        }));
        timeline.setCycleCount(3);
        timeline.setOnFinished(event -> enableAnswerButtons());
        timeline.play();

    }

    private void disableAnswerButtons() {
        answerBtn1.setDisable(true);
        answerBtn2.setDisable(true);
        answerBtn3.setDisable(true);
        answerBtn4.setDisable(true);
    }

    private void enableAnswerButtons() {
        answerBtn1.setDisable(false);
        answerBtn2.setDisable(false);
        answerBtn3.setDisable(false);
        answerBtn4.setDisable(false);
        answerBtn1.setStyle("-fx-background-color: #FFEEB6;");
        answerBtn2.setStyle("-fx-background-color: #FFEEB6;");
        answerBtn3.setStyle("-fx-background-color: #FFEEB6;");
        answerBtn4.setStyle("-fx-background-color: #FFEEB6;");
    }

    private void finishQuiz() {
        try {
            ResultQuizController.totalQuestions = (attemptingQuiz.getNumberOfQuestions());
            ResultQuizController.correctAnswersCount = (correctAnswers);
            ResultQuizController.wrongAnswersCount = (wrongAnswers);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizPlayStyles/ResultQuiz.fxml"));
            Parent root = loader.load();


            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.show();
            scene.setFill(Color.TRANSPARENT);

            // Close the current quiz scene
            Stage currentStage = (Stage) txtTimer.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void finishQuizDueToTimeout() {
        finishQuiz();
    }

}