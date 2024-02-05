package quizzey.quizzey;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;

import java.io.IOException;

public class StudentAvailableQuizzesCardController extends StudentPageController {
    @FXML
    public Text txtQuizName;
    @FXML
    public TextField txtFieldCode;
    @FXML
    public Button btnEnter;

    public Quiz quiz;

    public  void initialize() {
        // Set up event handler for button click
        btnEnter.setOnAction(this::handleEnterButtonClick);
    }

    public void setData(Quiz quiz) {
        txtQuizName.setText(quiz.getQuizName());
    }

    private void handleEnterButtonClick(ActionEvent event) {
        if (!checkInput())
            return;

        quiz = QuizManager.getQuizByCode(txtFieldCode.getText().toUpperCase());
        // Disable the current stage
        Stage currentStage = (Stage) btnEnter.getScene().getWindow();
        currentStage.hide();

        // Create a new stage for the quiz
        Stage quizStage = new Stage();



        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizPlayStyles/StartQuiz.fxml"));
            AnchorPane newContent = loader.load();

            PlayQuizController.attemptingQuiz = quiz;
            PlayQuizController.attemptingStudent = loggedInStudent;
            ResultQuizController.student = loggedInStudent;
            ResultQuizController.quiz = quiz;

            Scene scene = new Scene(newContent);
            quizStage.setScene(scene);
            quizStage.setTitle("Quiz");
            quizStage.setResizable(false);


            // Show the quiz stage
            quizStage.show();

            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkInput() {
        if (txtFieldCode.getText().isEmpty()) {
            showAlert("The Entry Code can not be empty");
            return false;
        }
        if (QuizManager.getQuizByCode(txtFieldCode.getText()) == null) {
            showAlert("The Entry Code is Invalid");
            return false;
        }
        return true;
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


}
