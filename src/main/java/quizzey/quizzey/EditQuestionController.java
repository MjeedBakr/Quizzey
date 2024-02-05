package quizzey.quizzey;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import quizzey.quizzey.Quiz.Question;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class EditQuestionController extends AdminAddQuizController{
    public TextField txtFldQuizID;
    public Button btnShowQuestions;
    public TextField txtfldQuestionID;
    public CheckBox checkboxTrueAnswer;
    public TextField txtfldNewValue;
    public ChoiceBox<ValueTypes> choiceboxValueType;
    public Button btnUpdateValue;

    enum ValueTypes {Question, Choice1, Choice2, Choice3, Choice4};
    public ValueTypes selectedItem;
    private Stage currentQuestionsStage;

    public void initialize() throws IOException {
        super.initialize();
        choiceboxValueType.getItems().addAll(ValueTypes.Question, ValueTypes.Choice1, ValueTypes.Choice2, ValueTypes.Choice3, ValueTypes.Choice4);
        choiceboxValueType.setValue(choiceboxValueType.getItems().getFirst());

        // Add a listener to the ChoiceBox to handle selection changes
        choiceboxValueType.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                selectedItem = newValue;
            }
        });

        btnUpdateValue.setOnAction(event -> updateValue());
        btnShowQuestions.setOnAction(event -> showQuestions());


    }

    public void updateValue() {
        Quiz selectedQuiz = QuizManager.getQuizByID(txtFldQuizID.getText().toUpperCase());
        Question selectedQuestion = QuizManager.getQuestionByID(txtfldQuestionID.getText(), selectedQuiz.getQuestions());
        if (!checkInputEditQuestion())
            return;

        if (currentQuestionsStage != null && currentQuestionsStage.isShowing()) {
            currentQuestionsStage.close();
        }

        if (selectedItem != null) {
            switch (selectedItem) {
                case Question -> {
                    updateQuestion();
                    break;
                }
                case Choice1 -> {
                    updateAnswer(checkboxTrueAnswer.isSelected(), selectedQuestion.getAnswerText(selectedQuestion, 0));
                    break;
                }
                case Choice2 -> {
                    updateAnswer(checkboxTrueAnswer.isSelected(), selectedQuestion.getAnswerText(selectedQuestion, 1));
                    break;
                }
                case Choice3 -> {
                    updateAnswer(checkboxTrueAnswer.isSelected(), selectedQuestion.getAnswerText(selectedQuestion, 2));
                    break;
                }
                case Choice4 -> {
                    updateAnswer(checkboxTrueAnswer.isSelected(), selectedQuestion.getAnswerText(selectedQuestion, 3));
                }
            }
        }

    }

    public void updateAnswer(boolean isCorrectAnswer, String choiceID) {

        Quiz selectedQuiz = QuizManager.getQuizByID(txtFldQuizID.getText().toUpperCase());
        Question selectedQuestion = QuizManager.getQuestionByID(txtfldQuestionID.getText(), selectedQuiz.getQuestions());

        selectedQuestion.updateChoice(choiceID, txtfldNewValue.getText(), isCorrectAnswer);

        showAlert("Edit Question data successfully");
        showQuestions();
    }


    public void updateQuestion() {
        Quiz selectedQuiz = QuizManager.getQuizByID(txtFldQuizID.getText().toUpperCase());
        Question selectedQuestion = QuizManager.getQuestionByID(txtfldQuestionID.getText().toUpperCase(), selectedQuiz.getQuestions());
        selectedQuestion.setTheQuestion(txtfldNewValue.getText());

        showAlert("Edit Question data successfully");
        showQuestions();
    }

    public boolean checkInputEditQuestion() {

        if (isEmpty(txtFldQuizID)) {
            showAlert("Quiz ID Error", "Quiz ID can not be empty");
            return false;
        }

        if (isEmpty(txtfldQuestionID)) {
            showAlert("Question ID Error", "Question ID can not be empty");
            return false;
        }

        if (isEmpty(txtfldNewValue)) {
            showAlert("New Value", "New Value can not be empty");
            return false;
        }

        if (QuizManager.getQuizByID(txtFldQuizID.getText().toUpperCase()) == null) {
            showAlert("Quiz ID Error", "The specified Quiz ID was not found.");
            return false;
        }

        Quiz selectedQuiz = QuizManager.getQuizByID(txtFldQuizID.getText().toUpperCase());
        assert selectedQuiz != null;
        if (QuizManager.getQuestionByID(txtfldQuestionID.getText().toUpperCase(), selectedQuiz.getQuestions()) == null) {
            showAlert("QuestionID Error", "There is no Question with this ID");
            return false;
        }

        if (checkboxTrueAnswer.isSelected()&&selectedItem == ValueTypes.Question) {
            showAlert("!!!", "You can not set a Question as an answer");
            return false;
        }

        return true;
    }

    public void showQuestions() {

        Quiz selectedQuiz = QuizManager.getQuizByID(txtFldQuizID.getText().toUpperCase());

        if (selectedQuiz != null) {

            Stage questionsStage = new Stage();

            // Create a pane to hold the questions information
            ScrollPane questionsPane = createQuestionsPane(selectedQuiz);

            // Set the scene for the stage
            Scene questionScene = new Scene(questionsPane, 600, 400);
            questionsStage.setScene(questionScene);
            questionsStage.setTitle(selectedQuiz.getQuizName());

            questionsStage.show();
            currentQuestionsStage = questionsStage;
        } else {
            showAlert("Quiz not found", "The specified Quiz ID was not found.");
        }
    }

    private ScrollPane createQuestionsPane(Quiz quiz) {
        VBox questionsBox = new VBox(10);
        questionsBox.setAlignment(Pos.CENTER);
        questionsBox.setPadding(new Insets(10));

        for (Question question : quiz.getQuestions()) {
            VBox questionCard = createQuestionCard(question);
            questionsBox.getChildren().add(questionCard);
        }

        ScrollPane scrollPane = new ScrollPane(questionsBox);
        scrollPane.setFitToWidth(true);

        return scrollPane;
    }

    private VBox createQuestionCard(Question question) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPageStyles/ShowQuestionsCard.fxml"));
            VBox questionCard = loader.load();

            // Access the controller to set the values
            ShowQuestionsCardController cardController = loader.getController();
            cardController.txtQuestionID.setText("Question ID: " + question.getQuestionID());
            cardController.txtQuestion.setText(question.getTheQuestion());
            cardController.txtAnswer1.setText(getAnswerText(question, 1));
            cardController.txtAnswer2.setText(getAnswerText(question, 2));
            cardController.txtAnswer3.setText(getAnswerText(question, 3));
            cardController.txtAnswer4.setText(getAnswerText(question, 4));

            return questionCard;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAnswerText(Question question, int choiceNumber) {
        HashMap<String, Boolean> choices = question.getChoices();
        return choices.keySet().toArray(new String[0])[choiceNumber - 1];
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
