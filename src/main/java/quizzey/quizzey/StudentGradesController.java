package quizzey.quizzey;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import quizzey.quizzey.Quiz.QuizManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class StudentGradesController extends StudentPageController {

    @FXML
    private VBox quizzesVBox;

    public void initialize() throws IOException {
        super.initialize();


        HashMap<String, Double> attemptedQuizzesMap = loggedInStudent.getStudentGrade();

        // Populate the view with quiz names and grades
        for (String quizID : attemptedQuizzesMap.keySet()) {
            Double quizGrade = attemptedQuizzesMap.get(quizID);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentPageStyles/GradesCard.fxml"));
            HBox quizPane = loader.load();
            GradesCardController controller = loader.getController();

            controller.txtQuizName.setText(Objects.requireNonNull(QuizManager.getQuizByID(quizID)).getQuizName());
            controller.txtQuizGrade.setText(quizGrade + "%");

            HBox.setMargin(quizPane, new Insets(10));

            quizzesVBox.getChildren().add(quizPane);
        }
    }
}