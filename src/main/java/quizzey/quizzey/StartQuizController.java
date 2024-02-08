package quizzey.quizzey;

import javafx.animation.RotateTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class StartQuizController {

    public Circle c1;
    public Circle c2;
    public Circle c3;
    public Text play;

    public void initialize() {
        playRotation();

        c3.setOnMouseClicked(this::playQuiz);
        play.setOnMouseClicked(this::playQuiz);
    }

    public void playQuiz(MouseEvent event) {

        

        Stage stage = new Stage();

        try {
            // Load the FXML file of the new stage
            FXMLLoader loader = new FXMLLoader(getClass().getResource("QuizPlayStyles/PlayQuiz.fxml"));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);

            Stage currentStage = (Stage) c3.getScene().getWindow();

            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);

            // Show the new stage
            stage.show();

            // Close the current stage
            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playRotation() {
        setRotate(c1,true, 360, 10);
        setRotate(c2,true, 180, 18);
        setRotate(c3,true, 145, 24);
    }

    private void setRotate(Circle c, boolean reverse, int angle, int duration) {
        RotateTransition rt = new RotateTransition(Duration.seconds(duration), c);

        rt.setAutoReverse(reverse);

        rt.setByAngle(angle);
        rt.setDelay(Duration.seconds(0));
        rt.setRate(3);
        rt.setCycleCount(18);
        rt.play();
    }
}
