package quizzey.quizzey;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import quizzey.quizzey.StudentPage.StudentProfileController;
import quizzey.quizzey.Users.Student;

import java.io.IOException;

import static quizzey.quizzey.LoginPageController.loggedInStudent;

public class StudentPageController {

    public Button btnHome;
    public Button btnQuiz;
    public Button btnGrades;
    public Button btnProfile;

    public Button btnLogout;
    @FXML
    private VBox contentVBox;

    private Student loggenInStudent = new Student();
    StudentProfileController studentProfileController = new StudentProfileController();



    public void setLoggenInStudent(Student loggenInStudent) {

        this.loggenInStudent = loggenInStudent;
    }

    public void initialize() {

        // Set default content
        StudentPageController studentPageController = new StudentPageController();
        loadAndSetContent("StudentPageStyles/Overview.fxml", studentPageController);






        // Handle button actions
        btnHome.setOnAction(event -> loadAndSetContent("StudentPageStyles/Overview.fxml", studentPageController));
        btnProfile.setOnAction(event -> initializeProfile(studentProfileController));
        btnLogout.setOnAction(event -> logout());
    }

    private void loadAndSetContent(String fxmlFileName, Object controller) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            VBox newContent = loader.load();

            loader.setController(controller);
            // Set the new content in the VBox
            contentVBox.getChildren().setAll(newContent);
        } catch (IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }
    }

    private void initializeProfile(StudentProfileController controller) {
        controller.initializeProfile(loggedInStudent);
        loadAndSetContent("StudentPageStyles/Profile.fxml", controller);
    }
    private void logout() {
        // Handle logout logic
    }


}


