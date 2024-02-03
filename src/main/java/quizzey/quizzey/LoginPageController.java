package quizzey.quizzey;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import quizzey.quizzey.Users.Admin;
import quizzey.quizzey.Users.Student;
import quizzey.quizzey.Users.User;

public class LoginPageController {

    public Label labelSelector;
    public ChoiceBox<User.Role> selectorAccount;
    public Label labelEmail;
    public TextField txtFieldEmail;
    public Label labelPassword;
    public PasswordField txtFieldPassword;
    public Button btnLogin;
    public User.Role selectedRule;
    public Label loginMessage;
    public static Student loggedInStudent = new Student();

    public static Admin loggedInAdmin = new Admin();


    public void setRandomData() {
        Student stu1 = new Student("1", "Ali", "Ahmad", "Ali@Ali.com", "12345678", User.Role.STUDENT);
        Admin adm1 = new Admin("2", "Mohammed", "Admin", "Admin@Admin.com", "admin123", User.Role.ADMIN);
        Student.studentsList.add(stu1);
        Admin.adminsList.add(adm1);
    }




    public void initialize() {

        setRandomData();
        // Add roles to the ChoiceBox
        selectorAccount.getItems().addAll(User.Role.STUDENT, User.Role.ADMIN);

        // Set Admin as a default role
        selectorAccount.setValue(selectorAccount.getItems().get(1));

        // Add a listener to the ChoiceBox to handle selection changes
        selectorAccount.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Update label or perform other actions based on the selected role
                labelSelector.setText("Selected Role: " + newValue);
                selectedRule = newValue;
            }
        });

        btnLogin.setOnMouseClicked(mouseEvent -> handleLoginButtonAction());

    }

    private void handleLoginButtonAction() {
        // Implement login functionality based on the selected role
        User.Role selectedRole = selectorAccount.getValue();
        String email = txtFieldEmail.getText();
        String password = txtFieldPassword.getText();

        if (email.isEmpty()) {
            loginMessage.setText("Please enter your email ");
            return;
        }
        if (password.isEmpty()) {
            loginMessage.setText("Please enter a password");
            return;
        }

        boolean loginSuccessful = performLogin(selectedRole, email, password);


        if (loginSuccessful) {
            // Successful login logic
            loginMessage.setText("Login Successful");
            loginMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold");  // Set text color to green
        } else {
            // Failed login logic
            loginMessage.setText("Login Failed");
            loginMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold");  // Set text color to red
        }
    }

    private boolean performLogin(User.Role role, String email, String password) {

        switch (role) {
            case ADMIN -> {
                loggedInAdmin = Admin.getAdminByEmailAndPassword(email, password);
                // Authenticate admin
                // Add admin authentication logic here
                if (loggedInAdmin != null) {
                    openAdminPage(loggedInAdmin);
                    return true;
                }
                return false;
            }
            case STUDENT -> {
                loggedInStudent = Student.getStudentByEmailAndPassword(email, password);
                // Authenticate student
                // Add student authentication logic here

                if (loggedInStudent != null) {
                    openStudentPage(loggedInStudent);
                    return true; // Login successful
                }
                return false;
            }

        }

        return false;
    }

    private void openAdminPage(Admin loggedInAdmin) {
    }

    private void openStudentPage(Student student) {
        try {
            // Load the FXML file for the student page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentPageStyles/StudentPage.fxml"));

            // Create a new scene
            Scene studentScene = new Scene(loader.load());

            // Get the controller from the loader
            StudentPageController studentPageController = loader.getController();


            // Get the stage from the current scene
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();

            // Set the new scene on the stage
            currentStage.setScene(studentScene);

            // Show the stage
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
