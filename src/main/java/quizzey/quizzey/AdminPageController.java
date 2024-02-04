package quizzey.quizzey;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;
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

    public void initialize() throws IOException {
            setStudents();
            if (btnAddStudentToList != null)
            {
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

    public void addStudentToList() throws IOException {
        if (checkInput())
        {
            Student student = new Student(txtFieldStudentID.getText(), txtFieldStudentName.getText(), txtFieldStudentLastName.getText(), txtFieldStudentEmail.getText(), txtFieldStudentPassword.getText(), User.Role.STUDENT);
            Student.studentsList.add(student);
            setStudent(student);
            txtMessage.setText("Add Student Data Successfully");
            txtMessage.setFill(Color.GREEN);
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

            // Use VBox.setMargin() to add spacing between cards
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

        if (Student.getStudentByID(txtFieldStudentID.getText()) != null) {
            txtMessage.setText("There is a student with this ID");
            txtMessage.setFill(Color.RED);
            return false;
        }

        if (txtFieldStudentID.getText().isEmpty()) {
            txtMessage.setText("The ID can not be empty");
            txtMessage.setFill(Color.RED);
            return false;
        }

        if (txtFieldStudentName.getText().isEmpty() || txtFieldStudentLastName.getText().isEmpty()) {
            txtMessage.setText("The name can not be empty");
            txtMessage.setFill(Color.RED);
            return false;
        }

        if (!Student.isValidEmail(txtFieldStudentEmail.getText())) {
            txtMessage.setText("Invalid Email");
            txtMessage.setFill(Color.RED);
            return false;
        }
        if (!Student.isValidPassword(txtFieldStudentPassword.getText())) {
            txtMessage.setText("Invalid Password");
            txtMessage.setFill(Color.RED);
            return false;
        }

        return true;
    }
}
