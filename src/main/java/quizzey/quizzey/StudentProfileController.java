package quizzey.quizzey;

import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import quizzey.quizzey.LoginPageController;
import quizzey.quizzey.StudentPageController;
import quizzey.quizzey.Users.Student;

import java.io.IOException;

public class StudentProfileController extends StudentPageController {

    public Text txtID;
    public Text txtFirstName;
    public Text txtLastName;
    public CheckBox checkboxShowPassword;
    public Text txtEmail;
    public Text txtPassword;
    public Text txtRule;


    @Override
    public void initialize() throws IOException {
        super.initialize();

        if (loggedInStudent != null) {
            setIDValue(loggedInStudent.getPersonID());
            setNameValue(loggedInStudent.getFirstName(), loggedInStudent.getLastName());
            setEmailValue(loggedInStudent.getEmail());
            setPasswordValue(loggedInStudent.getPassword());
            setRuleValue("Student");

            checkboxShowPassword.setOnAction(event -> togglePasswordVisibility());
        }
    }

    public void setIDValue(String id) {
        txtID.setText(id);
    }

    public void setNameValue(String firstName, String lastName) {
        txtFirstName.setText(firstName);
        txtLastName.setText(lastName);
    }

    public void setEmailValue(String email) {
        txtEmail.setText(email);
    }

    public void setPasswordValue(String password) {
        txtPassword.setText(password);
    }

    public void setRuleValue(String rule) {
        txtRule.setText(rule);
    }

    private void togglePasswordVisibility() {
        txtPassword.setVisible(checkboxShowPassword.isSelected());
    }

}
