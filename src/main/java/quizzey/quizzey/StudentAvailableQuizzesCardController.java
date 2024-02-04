package quizzey.quizzey;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import quizzey.quizzey.Quiz.Quiz;

public class StudentAvailableQuizzesCardController {
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
        this.quiz = quiz;
        txtQuizName.setText(quiz.getQuizName());
    }

    private void handleEnterButtonClick(ActionEvent event) {
    }


}
