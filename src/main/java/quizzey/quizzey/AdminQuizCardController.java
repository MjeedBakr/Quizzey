package quizzey.quizzey;

import javafx.scene.text.Text;
import quizzey.quizzey.Quiz.Quiz;

public class AdminQuizCardController {
    public Text txtQuizName;
    public Text txtQuizID;
    public Quiz quiz;

    public  void initialize() {


    }

    public void setData(Quiz quiz) {
        this.quiz = quiz;
        txtQuizName.setText(quiz.getQuizName());
        txtQuizID.setText(quiz.getQuizID());
    }
}
