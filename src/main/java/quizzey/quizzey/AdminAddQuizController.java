package quizzey.quizzey;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
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

    public void initialize() throws IOException {
        super.initialize();
        setQuizzes();

        btnAddQuestions.setOnAction(event -> addQuestions());
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

        // You can print or handle the question here if needed
        System.out.println("Added Question: " + question.getTheQuestion());
        System.out.println("  Question ID: " + question.getQuestionID());
        System.out.println("  Question: " + question.getTheQuestion());
        System.out.println("  Choices: " + question.getChoices());
    }

    public void addQuestions() {

        quiz.setQuizID(QuizManager.generateQuizID());
        quiz.setQuizName(txtFieldQuizName.getText());
        quiz.setQuizTimer((short) Integer.parseInt(txtFieldQuizTimer.getText()));
        quiz.setQuizEntryCode(txtFieldEntryCode.getText());
        quiz.setNumberOfQuestions((short) Integer.parseInt(txtFieldNumberOfQuestions.getText()));



        // Create a new stage
        Stage stage = new Stage();

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
                stage.setScene(new Scene(root));
                stage.showAndWait(); // Wait for the user to close the AddQuestionsCard stage
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        quiz.setQuestions(questionsList);
        questionsList.clear();
        QuizManager.quizzesList.add(quiz);
        loadAndSetContent("AdminPageStyles/AdminAddQuiz.fxml");

    }
}
