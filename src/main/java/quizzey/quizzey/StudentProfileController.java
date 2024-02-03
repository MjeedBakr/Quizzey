package quizzey.quizzey;

import javafx.scene.text.Text;
import quizzey.quizzey.LoginPageController;
import quizzey.quizzey.StudentPageController;
import quizzey.quizzey.Users.Student;

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
    public void initialize() {
        super.initialize();

        if (loggedInStudent != null) {
            setIDValue(loggedInStudent.getPersonID());
            setNameValue(loggedInStudent.getFirstName() + " " + loggedInStudent.getLastName());
        }
    }

    @Override
    public void loadAndSetContent(String fxmlFileName) {
        super.loadAndSetContent(fxmlFileName);
    }
}
