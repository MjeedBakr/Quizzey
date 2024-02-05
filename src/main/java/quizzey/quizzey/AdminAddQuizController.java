package quizzey.quizzey;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import quizzey.quizzey.Quiz.Question;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;

import java.io.IOException;
import java.util.ArrayList;

public class AdminAddQuizController extends AdminPageController{
    public HBox quizCardContainer;
    public TextField txtFieldQuizName;
    public TextField txtFieldQuizTimer;
    public TextField txtFieldEntryCode;
    public TextField txtFieldNumberOfQuestions;
    public Button btnAddQuestions;

    public Quiz quiz = new Quiz();
    public static ArrayList<Question> questionsList = new ArrayList<Question>();
    public Text txtMessageAddQuiz;

    public void initialize() throws IOException {
        super.initialize();
        setQuizzes();

        if (btnAddQuestions != null) {
            // set Focus when Enter key is pressed
            txtFieldQuizName.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    txtFieldQuizTimer.requestFocus();
                }
            });

            txtFieldQuizTimer.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    txtFieldEntryCode.requestFocus();
                }
            });

            txtFieldEntryCode.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    txtFieldNumberOfQuestions.requestFocus();
                }
            });

            txtFieldNumberOfQuestions.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    addQuestions(); // Simulate button click
                }
            });

            btnAddQuestions.setOnAction(event -> addQuestions());
        }

    }

    public void setQuizzes() throws IOException {
        for (Quiz quiz : QuizManager.quizzesList) {
            AdminQuizCardController adminQuizCardController;
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AdminPageStyles/QuizCard.fxml"));
            HBox cardBox = fxmlLoader.load();
            adminQuizCardController = fxmlLoader.getController();
            adminQuizCardController.setData(quiz);

            // Use HBox.setMargin() to add spacing between cards
            HBox.setMargin(cardBox, new Insets(10));

            quizCardContainer.getChildren().add(cardBox);
        }
    }

    public void addQuestion(Question question) {

        // Add the question to the list of questions in the current quiz
        question.setQuestionID("Q" + (questionsList.size() + 1));
        questionsList.add(question);

        // print the question for checking
        System.out.println("Added Question: " + question.getTheQuestion());
        System.out.println("  Question ID: " + question.getQuestionID());
        System.out.println("  Question: " + question.getTheQuestion());
        System.out.println("  Choices: " + question.getChoices());
    }

    public void addQuestions() {

        if (!checkInputAddQuiz())
            return;

        quiz.setQuizID(QuizManager.generateQuizID());
        quiz.setQuizName(txtFieldQuizName.getText());
        quiz.setQuizTimer((short) Integer.parseInt(txtFieldQuizTimer.getText()));
        quiz.setQuizEntryCode(txtFieldEntryCode.getText());
        quiz.setNumberOfQuestions((short) Integer.parseInt(txtFieldNumberOfQuestions.getText()));



        // Create a new stage
        Stage stage = new Stage();
        stage.initStyle(StageStyle.TRANSPARENT);

        for (int i = 0; i < quiz.getNumberOfQuestions(); i++) {

            Question question = new Question();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPageStyles/AddQuestionsCard.fxml"));
                Parent root = loader.load();

                // Access the controller of the AddQuestionsCard
                AddQuestionsCardController controller = loader.getController();

                // Set AdminAddQuizController as a reference in AddQuestionsCardController
                controller.setAdminAddQuizController(this);


                stage.setTitle("Add Question Number " + (i + 1));
                Scene scene = new Scene(root);
                scene.setFill(Color.TRANSPARENT);
                stage.setScene(scene);
                stage.showAndWait(); // Wait for the user to close the AddQuestionsCard stage
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        quiz.setQuestions(questionsList);
        QuizManager.quizzesList.add(quiz);
        questionsList.clear();
        loadAndSetContent("AdminPageStyles/AdminAddQuiz.fxml");

    }


    public boolean checkInputAddQuiz() {
        if (isEmpty(txtFieldQuizName) ||
                isEmpty(txtFieldQuizTimer) ||
                isEmpty(txtFieldEntryCode) ||
                isEmpty(txtFieldNumberOfQuestions)) {
            updateMessage("Please fill in all fields.", Color.RED, txtMessageAddQuiz);
            return false;
        }

        if (!isNumeric(txtFieldQuizTimer.getText()) || !isNumeric(txtFieldNumberOfQuestions.getText())) {
            updateMessage("Please enter valid numeric values for Quiz Timer and Number of Questions.", Color.RED, txtMessageAddQuiz);
            return false;
        }

        int quizTimer = Integer.parseInt(txtFieldQuizTimer.getText());
        int numberOfQuestions = Integer.parseInt(txtFieldNumberOfQuestions.getText());

        if (quizTimer < 1 || quizTimer > 10) {
            updateMessage("Quiz time must be between 1 and 10 minuts.", Color.RED, txtMessageAddQuiz);
            return false;
        }

        if (numberOfQuestions < 1 || numberOfQuestions > 10) {
            updateMessage("Number of questions must be between 1 and 10.", Color.RED, txtMessageAddQuiz);
            return false;
        }

        String entryCode = txtFieldEntryCode.getText();
        if (!entryCode.matches("[A-Za-z0-9]{6}")) {
            updateMessage("Entry code must be 6 letters and numbers only.", Color.RED, txtMessageAddQuiz);
            return false;
        }

        return true;
    }

    public boolean isNumeric(String str) {
        return str.matches("\\d+");
    }



}
