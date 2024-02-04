package quizzey.quizzey;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import quizzey.quizzey.Users.Student;

import java.io.IOException;

public class EditStudentController extends AdminPageController{
    public Button btnEditStudentFromList;
    public Button btnDeleteStudent;
    public Text txtMessage;
    public CheckBox firstNameCheckbox;
    public CheckBox lastNameCheckbox;
    public CheckBox emailCheckbox;
    public CheckBox passwordChechBox;

    public void initialize() throws IOException {
        super.initialize();

        if (btnAddStudentToList != null)
        {
                    setStudents();
        }


        firstNameCheckbox.setOnAction(event -> txtFieldStudentName.setEditable(firstNameCheckbox.isSelected()));
        lastNameCheckbox.setOnAction(event -> txtFieldStudentLastName.setEditable(lastNameCheckbox.isSelected()));
        emailCheckbox.setOnAction(event -> txtFieldStudentEmail.setEditable(emailCheckbox.isSelected()));
        passwordChechBox.setOnAction(event -> txtFieldStudentPassword.setEditable(passwordChechBox.isSelected()));

        btnEditStudentFromList.setOnAction(event -> editStudentInfo());
        btnDeleteStudent.setOnAction(event -> deleteStudent());
    }

    public void editStudentInfo() {

        if (txtFieldStudentID.getText().isEmpty()) {
            txtMessage.setText("the id can not be empty");
            txtMessage.setFill(Color.RED);
            return;
        }

        boolean areSelected = (emailCheckbox.isSelected() && passwordChechBox.isSelected()
        && firstNameCheckbox.isSelected() && lastNameCheckbox.isSelected());

        if (areSelected) {
            txtMessage.setText("You did not select any field to edit");
            txtMessage.setFill(Color.RED);
            return;
        }

        Student getStudent = Student.getStudentByID(txtFieldStudentID.getText())  ;

        if (getStudent == null) {
            txtMessage.setText("There is no student with this ID");
            txtMessage.setFill(Color.RED);
            return;
        }

        else {

            if (txtFieldStudentName.isEditable()) {
                if (txtFieldStudentName.getText().isEmpty()) {
                    txtMessage.setText("The name can not be empty");
                    txtMessage.setFill(Color.RED);
                    return;

                }
                else {
                    getStudent.setFirstName(txtFieldStudentName.getText());
                }
            }

            if (txtFieldStudentLastName.isEditable()) {
                if (txtFieldStudentLastName.getText().isEmpty()) {
                    txtMessage.setText("The name can not be empty");
                    txtMessage.setFill(Color.RED);
                    return;

                }
                else {
                    getStudent.setLastName(txtFieldStudentLastName.getText());
                }
            }

            if (txtFieldStudentEmail.isEditable()) {
                if (txtFieldStudentEmail.getText().isEmpty()) {
                    txtMessage.setText("The email can not be empty");
                    txtMessage.setFill(Color.RED);
                    return;
                }
                else if(!Student.isValidEmail(txtFieldStudentEmail.getText())){
                    txtMessage.setText("Invalid Email");
                    txtMessage.setFill(Color.RED);
                    return;
                }
                else {
                    getStudent.setEmail(txtFieldStudentEmail.getText());
                }
            }

            if (txtFieldStudentPassword.isEditable()) {
                if (txtFieldStudentPassword.getText().isEmpty()) {
                    txtMessage.setText("The password can not be empty");
                    txtMessage.setFill(Color.RED);
                    return;
                }
                else if(!Student.isValidPassword(txtFieldStudentPassword.getText())){
                    txtMessage.setText("Invalid password");
                    txtMessage.setFill(Color.RED);
                    return;
                }
                else {
                    getStudent.setPassword(txtFieldStudentPassword.getText());
                }
            }





            txtMessage.setText("Edit Student Data Successfully");
            txtMessage.setFill(Color.GREEN);
            loadAndSetContent("AdminPageStyles/EditStudent.fxml");
        }

    }

    public void deleteStudent() {

        if (txtFieldStudentID.getText().isEmpty()) {
            txtMessage.setText("the id can not be empty");
            txtMessage.setFill(Color.RED);
            return;
        }
        Student getStudent = Student.getStudentByID(txtFieldStudentID.getText())  ;

        if (getStudent == null) {
            txtMessage.setText("There is no student with this ID");
            txtMessage.setFill(Color.RED);
            return;
        }
        else {
            Student.studentsList.remove(getStudent);

            txtMessage.setText("Delete Student Data Successfully");
            txtMessage.setFill(Color.GREEN);
            loadAndSetContent("AdminPageStyles/EditStudent.fxml");
        }

    }


}
