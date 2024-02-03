package quizzey.quizzey;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import quizzey.quizzey.Users.Student;

import java.io.IOException;

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


     public void initialize() {


        // Handle button actions
        btnHome.setOnAction(event -> loadAndSetContent("StudentPageStyles/StudentPage.fxml"));
        btnProfile.setOnAction(event -> loadAndSetContent("StudentPageStyles/Profile.fxml"));
        btnLogout.setOnAction(event -> logout());
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

    public void logout() {
        // Handle logout logic
    }


}


