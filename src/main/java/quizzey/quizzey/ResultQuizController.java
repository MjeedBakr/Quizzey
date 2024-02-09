package quizzey.quizzey;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Users.Student;

import java.io.File;
import java.io.IOException;

public class ResultQuizController {

    public Text txtStudentName;
    public Text txtQuizName;
    public Text txtQuizTime;
    public Text txtNumberOfQuestions;
    public Text txtCorrectAnswers;
    public Text txtWrongAnswers;
    public Text txtGrade;
    public Button btnBackToHomePage;

    public static Student student;
    public static Quiz quiz;
    public static int totalQuestions;
    public static int correctAnswersCount;
    public static int wrongAnswersCount;
    public static double grade;

    public void initialize() {
        if (correctAnswersCount > wrongAnswersCount)
            playWin();
        else
            playLose();

        grade = getGrade();

        txtStudentName.setText(student.getFirstName() + " " + student.getLastName());
        txtQuizName.setText(quiz.getQuizName());
        txtQuizTime.setText(calculateTime(quiz.getQuizTimer()));
        txtNumberOfQuestions.setText(String.valueOf(totalQuestions));
        txtCorrectAnswers.setText(String.valueOf(correctAnswersCount));
        txtWrongAnswers.setText(String.valueOf(wrongAnswersCount));
        txtGrade.setText("Grade: " + grade + "%");

        Student.addGradeForStudent(student, grade, quiz.getQuizID());

        btnBackToHomePage.setOnAction(event -> {
            backToHomePage();
        });

    }

    public void playWin() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("win.mp3").toExternalForm()));

        mediaPlayer.play();
    }

    public void playLose() {
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(getClass().getResource("lose.mp3").toExternalForm()));

        mediaPlayer.play();
    }

    private String calculateTime(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }


    public double getGrade() {
        return  ((double) correctAnswersCount /totalQuestions) * 100;
    }

    public void backToHomePage () {

        try {
            PlayQuizController.attemptingQuiz = null;
            PlayQuizController.attemptingStudent = null;
            PlayQuizController.currentQuestionIndex = 0;
            ResultQuizController.student = null;
            ResultQuizController.quiz = null;
            ResultQuizController.wrongAnswersCount = 0;
            ResultQuizController.correctAnswersCount = 0;
            ResultQuizController.grade = 0;
            ResultQuizController.totalQuestions = 0;

            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentPageStyles/StudentPage.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) btnBackToHomePage.getScene().getWindow();
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
