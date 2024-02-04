package quizzey.quizzey;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quizzey.quizzey.Quiz.Question;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;

import java.io.IOException;
import java.util.HashMap;

public class AddQuestionsCardController {
    public TextField txtFieldQuestion;
    public TextField txtFieldChoice1;
    public TextField txtFieldChoice2;
    public TextField txtFieldChoice3;
    public TextField txtFieldChoice4;
    public CheckBox checkboxChoice1;
    public CheckBox checkboxChoice2;
    public CheckBox checkboxChoice3;
    public CheckBox checkboxChoice4;
    public Button btnAddQuestion;

    private AdminAddQuizController adminAddQuizController;

    public void setAdminAddQuizController(AdminAddQuizController adminAddQuizController) {
        this.adminAddQuizController = adminAddQuizController;
    }

    public void initialize() throws IOException {
        btnAddQuestion.setOnAction(event -> addQuestion());


    }



    public void addQuestion() {
        HashMap<String, Boolean> choices = new HashMap<>();

        // Get the question details from the text fields, checkboxes, etc.
        String questionText = txtFieldQuestion.getText();
        String choice1 = txtFieldChoice1.getText();
        String choice2 = txtFieldChoice2.getText();
        String choice3 = txtFieldChoice3.getText();
        String choice4 = txtFieldChoice4.getText();
        boolean isCorrect1 = checkboxChoice1.isSelected();
        boolean isCorrect2 = checkboxChoice2.isSelected();
        boolean isCorrect3 = checkboxChoice3.isSelected();
        boolean isCorrect4 = checkboxChoice4.isSelected();

        choices.put(choice1, isCorrect1);
        choices.put(choice2, isCorrect2);
        choices.put(choice3, isCorrect3);
        choices.put(choice4, isCorrect4);


        Question question = new Question(questionText, choices);


        // Pass the question to the AdminAddQuizController
        adminAddQuizController.addQuestion(question);

        // Close the AddQuestionsCard stage
        Stage stage = (Stage) btnAddQuestion.getScene().getWindow();
        stage.close();
    }
}
