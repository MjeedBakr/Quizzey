package quizzey.quizzey;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;

import java.io.IOException;

public class StudentQuizzesController extends StudentPageController{

    @FXML
    public VBox cardContainer;

    @Override
    public void initialize() throws IOException {
        super.initialize();

        if (loggedInStudent != null) {
            setQuizzes();
        }
    }


    public void setQuizzes() throws IOException {
        for (Quiz quiz : QuizManager.quizzesList) {
            StudentAvailableQuizzesCardController studentAvailableQuizzesCardController;
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("StudentPageStyles/StudentAvailableQuizzesCard.fxml"));
            HBox cardBox = fxmlLoader.load();
            studentAvailableQuizzesCardController = fxmlLoader.getController();
            studentAvailableQuizzesCardController.setData(quiz);

            // Use VBox.setMargin() to add spacing between cards
            HBox.setMargin(cardBox, new Insets(10));

            cardContainer.getChildren().add(cardBox);
        }
    }
}
