package quizzey.quizzey;

import java.io.*;
import java.util.ArrayList; // Import ArrayList class
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

public class StudentQuizzesController extends StudentPageController implements Serializable {

    @FXML
    public VBox cardContainer;

    @Override
    public void initialize() throws IOException {
        super.initialize();
        setQuizzes();

        if (loggedInStudent != null) {
            // Load quizzes from file
            loadQuizzesFromFile();
        }
    }

    private void loadQuizzesFromFile() {
        try {
            // Read the quiz objects from file
            FileInputStream fileIn = new FileInputStream("quiz.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);

            // Use proper generics to cast the object
            ArrayList<Quiz> loadedQuizzes = (ArrayList<Quiz>) objectIn.readObject();

            // Set the loaded quizzes to the QuizManager
            QuizManager.quizzesList = loadedQuizzes;

            // Close the streams
            objectIn.close();
            fileIn.close();

            // Populate UI with loaded quiz data
            setQuizzes();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
