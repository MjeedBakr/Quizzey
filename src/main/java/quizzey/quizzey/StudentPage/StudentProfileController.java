package quizzey.quizzey.StudentPage;

import javafx.scene.text.Text;
import quizzey.quizzey.StudentPageController;
import quizzey.quizzey.Users.Student;

public class StudentProfileController extends StudentPageController {

    public Text IDValue;
    public Text nameValue;


    public void initializeProfile(Student student) {
        setIDValue(student.getPersonID());
        setNameValue(student.getFirstName());
        // You can add more initialization logic if needed
    }

    public void setIDValue(String id) {
        IDValue.setText(id);
    }

    public void setNameValue(String name) {
        nameValue.setText(name);
    }

}
