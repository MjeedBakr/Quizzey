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
    public CheckBox passwordCheckBox;

    public void initialize() throws IOException {
        super.initialize();

        if (btnAddStudentToList == null) {
                    setStudents();
        }


        firstNameCheckbox.setOnAction(event -> txtFieldStudentName.setEditable(firstNameCheckbox.isSelected()));
        lastNameCheckbox.setOnAction(event -> txtFieldStudentLastName.setEditable(lastNameCheckbox.isSelected()));
        emailCheckbox.setOnAction(event -> txtFieldStudentEmail.setEditable(emailCheckbox.isSelected()));
        passwordCheckBox.setOnAction(event -> txtFieldStudentPassword.setEditable(passwordCheckBox.isSelected()));

        btnEditStudentFromList.setOnAction(event -> editStudentInfo());
        btnDeleteStudent.setOnAction(event -> deleteStudent());
    }

    public void editStudentInfo() {

        if (isEmpty(txtFieldStudentID)) {
            updateMessage("the id can not be empty", Color.RED, txtMessage);
            return;
        }

        boolean areSelected = (emailCheckbox.isSelected() || passwordCheckBox.isSelected()
        || firstNameCheckbox.isSelected() || lastNameCheckbox.isSelected());

        if (!areSelected) {
            updateMessage("You did not select any field to edit", Color.RED, txtMessage);
            return;
        }

        Student getStudent = Student.getStudentByID(txtFieldStudentID.getText())  ;

        if (getStudent == null) {
            updateMessage("There is no student with this ID", Color.RED, txtMessage);
            return;
        }

        else {

            if (txtFieldStudentName.isEditable()) {
                if (isEmpty(txtFieldStudentName)) {
                    updateMessage("The name can not be empty", Color.RED, txtMessage);
                    return;

                }
                else {
                    getStudent.setFirstName(txtFieldStudentName.getText());
                }
            }

            if (txtFieldStudentLastName.isEditable()) {
                if (isEmpty(txtFieldStudentLastName)) {
                    updateMessage("The name can not be empty", Color.RED, txtMessage);
                    return;

                }
                else {
                    getStudent.setLastName(txtFieldStudentLastName.getText());
                }
            }

            if (txtFieldStudentEmail.isEditable()) {
                if (isEmpty(txtFieldStudentEmail)) {
                    updateMessage("The email can not be empty", Color.RED, txtMessage);
                    return;
                }
                else if(!Student.isValidEmail(txtFieldStudentEmail.getText())){
                    updateMessage("Invalid Email", Color.RED, txtMessage);
                    return;
                }
                else {
                    getStudent.setEmail(txtFieldStudentEmail.getText());
                }
            }

            if (txtFieldStudentPassword.isEditable()) {
                if (isEmpty(txtFieldStudentPassword)) {
                    updateMessage("The password can not be empty", Color.RED, txtMessage);
                    return;
                }
                else if(!Student.isValidPassword(txtFieldStudentPassword.getText())){
                    updateMessage("Invalid password", Color.RED, txtMessage);
                    return;
                }
                else {
                    getStudent.setPassword(txtFieldStudentPassword.getText());
                }
            }




            showAlert("Edit Student Data Successfully");
            loadAndSetContent("AdminPageStyles/EditStudent.fxml");
        }

    }

    public void deleteStudent() {

        if (isEmpty(txtFieldStudentID)) {
            updateMessage("the id can not be empty", Color.RED, txtMessage);
            return;
        }
        Student getStudent = Student.getStudentByID(txtFieldStudentID.getText())  ;

        if (getStudent == null) {
            updateMessage("There is no student with this ID", Color.RED, txtMessage);
            return;
        }
        else {
            Student.studentsList.remove(getStudent);

            showAlert("Delete Student Data Successfully");
            loadAndSetContent("AdminPageStyles/EditStudent.fxml");
        }

    }


}
