package quizzey.quizzey;

import javafx.scene.text.Text;
import quizzey.quizzey.Users.Student;

public class StudentCardController {
    public Text txtStudentID;
    public Text txtStudentName;

    public Student student;

    public void setData(Student student) {
        txtStudentID.setText(student.getPersonID());
        txtStudentName.setText(student.getFirstName());
    }

    public void initialize() {


    }

}
