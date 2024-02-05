package quizzey.quizzey;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import quizzey.quizzey.Users.Student;

import java.io.IOException;
import java.util.Objects;

public class StudentPageController {

    @FXML
    public Button btnHome;
    @FXML
    private Button btnQuiz;
    @FXML
    public Button btnGrades;
    @FXML
    public Button btnProfile;

    @FXML
    public Button btnLogout;
    @FXML
    public AnchorPane contentVBox;

    public Student loggedInStudent = LoginPageController.loggedInStudent;


     public void initialize() throws IOException {


        // Handle button actions
        btnHome.setOnAction(event -> loadAndSetContent("StudentPageStyles/StudentPage.fxml"));
        btnQuiz.setOnAction(event -> loadAndSetContent("StudentPageStyles/StudentQuizzes.fxml"));
        btnProfile.setOnAction(event -> loadAndSetContent("StudentPageStyles/StudentProfile.fxml"));
        btnGrades.setOnAction(event -> loadAndSetContent("StudentPageStyles/StudentGrades.fxml"));
        btnLogout.setOnAction(event -> {
            try {
                logout();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void loadAndSetContent(String fxmlFileName) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));

            // Set the controller for the loaded FXML (if any)
            Object controller = loader.getController();

            AnchorPane newContent = loader.load();


            // Set the new content in the Anchor Pane
            contentVBox.getChildren().setAll(newContent);



        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    public void logout() throws IOException {
        // Reset the logged-in user
        loggedInStudent = null;

        // Load the login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPageStyles/LoginPage.fxml"));
        Parent loginPageParent = loader.load();
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }


}


