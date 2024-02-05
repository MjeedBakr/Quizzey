package quizzey.quizzey;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;
import quizzey.quizzey.Users.Admin;
import quizzey.quizzey.Users.Student;
import quizzey.quizzey.Users.User;

import java.io.IOException;

public class AdminPageController {
    public AnchorPane contentVBox;
    public Button btnAddStudent;
    public Button btnAddQuiz;
    public Button btnEditStudent;
    public Button btnEditQuiz;
    public Button btnLogout;
    public HBox cardContainer;
    public TextField txtFieldStudentID;
    public TextField txtFieldStudentName;
    public TextField txtFieldStudentEmail;
    public TextField txtFieldStudentPassword;
    public Button btnAddStudentToList;
    public TextField txtFieldStudentLastName;
    public Text txtMessage;
    public Button btnEditQuestion;
    Admin loggenInAdmin = LoginPageController.loggedInAdmin;

    public void initialize() throws IOException {

        if (btnAddStudentToList != null) {
            // set Focus when Enter key is pressed
            txtFieldStudentID.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    txtFieldStudentName.requestFocus();
                }
            });

            txtFieldStudentName.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    txtFieldStudentLastName.requestFocus();
                }
            });

            txtFieldStudentLastName.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    txtFieldStudentEmail.requestFocus();
                }
            });

            txtFieldStudentEmail.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    txtFieldStudentPassword.requestFocus();
                }
            });

            txtFieldStudentPassword.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        addStudentToList(); // Simulate button click
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });

            setStudents();
            btnAddStudentToList.setOnAction(event -> {
                try {

                    addStudentToList();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }



        btnAddStudent.setOnAction(event -> loadAndSetContent("AdminPageStyles/AdminPage.fxml"));
        btnEditStudent.setOnAction(event -> loadAndSetContent("AdminPageStyles/EditStudent.fxml"));
        btnAddQuiz.setOnAction(event -> loadAndSetContent("AdminPageStyles/AdminAddQuiz.fxml"));
        btnEditQuiz.setOnAction(event -> loadAndSetContent("AdminPageStyles/EditQuiz.fxml"));
        btnEditQuestion.setOnAction(event -> loadAndSetContent("AdminPageStyles/EditQuestion.fxml"));
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
        loggenInAdmin = null;

        // Load the login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoginPageStyles/LoginPage.fxml"));
        Parent loginPageParent = loader.load();
        Scene loginPageScene = new Scene(loginPageParent);
        Stage stage = (Stage) btnLogout.getScene().getWindow();
        stage.setScene(loginPageScene);
        stage.show();
    }

    public void addStudentToList() throws IOException {
        if (checkInput())
        {
            Student student = new Student(txtFieldStudentID.getText(), txtFieldStudentName.getText(), txtFieldStudentLastName.getText(), txtFieldStudentEmail.getText(), txtFieldStudentPassword.getText(), User.Role.STUDENT);
            Student.studentsList.add(student);
            setStudent(student);
            updateMessage("Add Student Data Successfully", Color.GREEN, txtMessage);
        }


    }

    public void setStudents() throws IOException {
        for (Student stu : Student.studentsList) {
            StudentCardController studentCardController;
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AdminPageStyles/StudentCard.fxml"));
            HBox cardBox = fxmlLoader.load();
            studentCardController = fxmlLoader.getController();
            studentCardController.setData(stu);

            // Use HBox.setMargin() to add spacing between cards
            HBox.setMargin(cardBox, new Insets(10));

            cardContainer.getChildren().add(cardBox);
        }
    }

    public void setStudent(Student student) throws IOException {

            StudentCardController studentCardController;
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("AdminPageStyles/StudentCard.fxml"));
            HBox cardBox = fxmlLoader.load();
            studentCardController = fxmlLoader.getController();
            studentCardController.setData(student);

            // Use VBox.setMargin() to add spacing between cards
            HBox.setMargin(cardBox, new Insets(10));

            cardContainer.getChildren().add(cardBox);

    }

    public boolean checkInput() {
        String studentID = txtFieldStudentID.getText().trim(); // Trim to remove leading/trailing whitespace

        if (Student.getStudentByID(studentID) != null) {
            updateMessage("There is a student with this ID", Color.RED, txtMessage);
            return false;
        }

        if (isEmpty(txtFieldStudentID)) {
            updateMessage("The ID cannot be empty", Color.RED, txtMessage);
            return false;
        }

        if (!studentID.matches("[sS][tT]\\d{3}")) {
            updateMessage("Invalid student ID format. It must start with 'ST' followed by 3 digits.", Color.RED, txtMessage);
            return false;
        }

        if (isEmpty(txtFieldStudentName) || isEmpty(txtFieldStudentLastName)) {
            updateMessage("The name cannot be empty", Color.RED, txtMessage);
            return false;
        }

        if (!Student.isValidEmail(txtFieldStudentEmail.getText())) {
            updateMessage("Invalid Email", Color.RED, txtMessage);
            return false;
        }
        if (!Student.isValidPassword(txtFieldStudentPassword.getText())) {
            updateMessage("Invalid password", Color.RED, txtMessage);
            return false;
        }

        return true;
    }

    public boolean isEmpty(TextField textField) {
        return textField.getText().trim().isEmpty();
    }

    public void updateMessage(String message, Paint color, Text msgToEdit ) {
        msgToEdit.setText(message);
        msgToEdit.setFill(color);
        msgToEdit.setStyle("-fx-font-weight: bold");
    }

    public void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Successful Process");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
