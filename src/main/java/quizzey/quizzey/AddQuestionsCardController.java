package quizzey.quizzey;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quizzey.quizzey.Quiz.Question;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;

import java.io.IOException;
import java.util.HashMap;

public class AddQuestionsCardController {
    @FXML
    public TextField txtFieldQuestion;
    @FXML
    public TextField txtFieldChoice1;
    @FXML
    public TextField txtFieldChoice2;
    @FXML
    public TextField txtFieldChoice3;
    @FXML
    public TextField txtFieldChoice4;
    @FXML
    public CheckBox checkboxChoice1;
    @FXML
    public CheckBox checkboxChoice2;
    @FXML
    public CheckBox checkboxChoice3;
    @FXML
    public CheckBox checkboxChoice4;
    @FXML
    public Button btnAddQuestion;
    @FXML
    public CheckBox checkboxConfirm;
    private static boolean confirmSelected = false;

    private AdminAddQuizController adminAddQuizController;

    public void setAdminAddQuizController(AdminAddQuizController adminAddQuizController) {
        this.adminAddQuizController = adminAddQuizController;
    }

    public void initialize() throws IOException {
        btnAddQuestion.setOnAction(event -> addQuestion());

        // Set the initial state
        checkboxConfirm.setSelected(confirmSelected);

        // Listener to update the confirmSelected variable
        checkboxConfirm.setOnAction(event -> confirmSelected = checkboxConfirm.isSelected());

        //Handle enter key for txtFields
        txtFieldQuestion.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtFieldChoice1.requestFocus();
            }
        });

        txtFieldChoice1.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtFieldChoice2.requestFocus();
            }
        });

        txtFieldChoice2.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtFieldChoice3.requestFocus();
            }
        });

        txtFieldChoice3.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtFieldChoice4.requestFocus();
            }
        });

        txtFieldChoice4.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                addQuestion();
            }
        });

    }




    public void addQuestion() {
        HashMap<String, Boolean> choices = new HashMap<>();

        String questionText = txtFieldQuestion.getText();
        String choice1 = txtFieldChoice1.getText();
        String choice2 = txtFieldChoice2.getText();
        String choice3 = txtFieldChoice3.getText();
        String choice4 = txtFieldChoice4.getText();
        boolean isCorrect1 = checkboxChoice1.isSelected();
        boolean isCorrect2 = checkboxChoice2.isSelected();
        boolean isCorrect3 = checkboxChoice3.isSelected();
        boolean isCorrect4 = checkboxChoice4.isSelected();

        if (!checkInput())
            return;

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

    public boolean checkInput() {
        if (txtFieldQuestion.getText().trim().isEmpty() ||
                txtFieldChoice1.getText().trim().isEmpty() ||
                txtFieldChoice2.getText().trim().isEmpty() ||
                txtFieldChoice3.getText().trim().isEmpty() ||
                txtFieldChoice4.getText().trim().isEmpty()) {
            showAlert("Please fill in all fields.");
            return false;
        }

        int correctAnswerCount = 0;

        if (checkboxChoice1.isSelected()) {
            correctAnswerCount++;
        }
        if (checkboxChoice2.isSelected()) {
            correctAnswerCount++;
        }
        if (checkboxChoice3.isSelected()) {
            correctAnswerCount++;
        }
        if (checkboxChoice4.isSelected()) {
            correctAnswerCount++;
        }

        if (correctAnswerCount != 1) {
            showAlert("Please select exactly one correct answer.");
            return false;
        }

        if (checkboxConfirm.isSelected()) {
            boolean userConfirmed = showConfirmationDialog("Are you sure you want to add the question?");
            return userConfirmed;
        }
        return true;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Input Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private boolean showConfirmationDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(okButton, cancelButton);

        return alert.showAndWait().orElse(cancelButton) == okButton;
    }
}
