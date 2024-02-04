package quizzey.quizzey;

import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import quizzey.quizzey.LoginPageController;
import quizzey.quizzey.StudentPageController;
import quizzey.quizzey.Users.Student;

import java.io.IOException;

public class StudentProfileController extends StudentPageController {

    public Text IDValue;
    public Text nameValue;


    public void setIDValue(String id) {
        IDValue.setText(id);
    }

    public void setNameValue(String name) {
        nameValue.setText(name);
    }

    @Override
    public void initialize() throws IOException {
        super.initialize();

        if (loggedInStudent != null) {
            setIDValue(loggedInStudent.getPersonID());
            setNameValue(loggedInStudent.getFirstName() + " " + loggedInStudent.getLastName());
        }
    }

}
