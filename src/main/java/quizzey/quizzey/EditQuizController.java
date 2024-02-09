package quizzey.quizzey;

import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class EditQuizController extends AdminAddQuizController implements Serializable{
    public TextField txtFieldEditQuizName;
    public TextField txtFieldEditQuizTimer;
    public TextField txtFieldQuizID;
    public TextField txtFieldEditNumberOfQuestions;
    public Text txtMessageEditQuiz;
    public Button btnEditQuizInfo;
    public Button btnDeleteQuiz;
    public CheckBox checkboxEditQuizName;
    public CheckBox checkboxEditQuizNumberOfQuestion;
    public CheckBox checkboxEditQuizTime;

    public void initialize() throws IOException {
        super.initialize();

        checkboxEditQuizName.setOnAction(event -> txtFieldEditQuizName.setEditable(checkboxEditQuizName.isSelected()));
        checkboxEditQuizNumberOfQuestion.setOnAction(event -> txtFieldEditNumberOfQuestions.setEditable(checkboxEditQuizNumberOfQuestion.isSelected()));
        checkboxEditQuizTime.setOnAction(event -> txtFieldEditQuizTimer.setEditable(checkboxEditQuizTime.isSelected()));

        btnEditQuizInfo.setOnAction(event -> editQuiz());
        btnDeleteQuiz.setOnAction(event -> deleteQuiz());

        loadQuizFromFile();
    }

    private void loadQuizFromFile() {
        try {
            // Read the quiz object from file
            FileInputStream fileIn = new FileInputStream("quiz.ser");
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Quiz loadedQuiz = (Quiz) objectIn.readObject();

            // Display the loaded quiz data in the UI
            txtFieldEditQuizName.setText(loadedQuiz.getQuizName());
            txtFieldEditQuizTimer.setText(String.valueOf(loadedQuiz.getQuizTimer()));
            txtFieldEditNumberOfQuestions.setText(String.valueOf(loadedQuiz.getNumberOfQuestions()));

            // Close the streams
            objectIn.close();
            fileIn.close();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void editQuiz (){
        if (!checkInputEditQuiz())
            return;

        Quiz getQuiz = QuizManager.getQuizByID(txtFieldQuizID.getText().toUpperCase())  ;

        if (getQuiz == null) {
            updateMessage("There is no Quiz with this ID", Color.RED, txtMessageEditQuiz);
            return;
        }
        else {

            if (txtFieldEditQuizTimer.isEditable()) {
                if (isEmpty(txtFieldEditQuizTimer)) {
                    updateMessage("The quiz timer can not be empty", Color.RED, txtMessageEditQuiz);
                    return;
                }
                if (!isNumeric(txtFieldEditQuizTimer.getText())) {
                    updateMessage("Please enter valid numeric values for Quiz Timer", Color.RED, txtMessageEditQuiz);
                    return;
                }
                int quizTimer = Integer.parseInt(txtFieldEditQuizTimer.getText());
                if (quizTimer < 10 || quizTimer > 60) {
                    updateMessage("Quiz time must be between 10 and 60 seconds.", Color.RED, txtMessageEditQuiz);
                    return;
                }
                else {
                    getQuiz.setQuizTimer(Short.parseShort(txtFieldEditQuizTimer.getText()));
                }
            }


            if (txtFieldEditQuizName.isEditable()) {
                if (isEmpty(txtFieldEditQuizName)) {
                    updateMessage("The name can not be empty", Color.RED, txtMessageEditQuiz);
                    return;
                }
                else {
                    getQuiz.setQuizName(txtFieldEditQuizName.getText());
                }
            }


            if (txtFieldEditNumberOfQuestions.isEditable()) {
                if (isEmpty(txtFieldEditNumberOfQuestions)) {
                    updateMessage("The number of questions can not be empty", Color.RED, txtMessageEditQuiz);
                    return;
                }
                if (!isNumeric(txtFieldEditNumberOfQuestions.getText())) {
                    updateMessage("Please enter valid numeric values for Number of Questions.", Color.RED, txtMessageEditQuiz);
                    return;
                }
                int numberOfQuestions = Integer.parseInt(txtFieldEditNumberOfQuestions.getText());
                if (numberOfQuestions < 1 || numberOfQuestions > 10) {
                    updateMessage("Number of questions must be between 1 and 10.", Color.RED, txtMessageEditQuiz);
                    return;
                }
                else {
                    getQuiz.setNumberOfQuestions(Short.parseShort(txtFieldEditNumberOfQuestions.getText()));
                }
            }




            loadAndSetContent("AdminPageStyles/EditQuiz.fxml");
            showAlert("Edit Quiz Data Successfully");

        }

    }

    public void deleteQuiz() {
        if (!checkInputDeleteQuiz())
            return;


        Quiz getQuiz = QuizManager.getQuizByID(txtFieldQuizID.getText().toUpperCase())  ;

        if (getQuiz == null) {
            updateMessage("There is no Quiz with this ID", Color.RED, txtMessageEditQuiz);
            return;
        }
        else {
            QuizManager.quizzesList.remove(getQuiz);

            loadAndSetContent("AdminPageStyles/EditQuiz.fxml");
            showAlert("Delete Quiz Data Successfully");
        }

    }

    public boolean checkInputEditQuiz() {

        if (isEmpty(txtFieldQuizID)) {
            updateMessage("the id can not be empty", Color.RED, txtMessageEditQuiz);
            return false;
        }

        boolean areSelected = (checkboxEditQuizName.isSelected() || checkboxEditQuizTime.isSelected()
                || checkboxEditQuizNumberOfQuestion.isSelected());

        if (!areSelected) {
            updateMessage("You did not select any field to edit", Color.RED, txtMessageEditQuiz);
            return false;
        }



        return true;
    }

    public boolean checkInputDeleteQuiz() {
        if (isEmpty(txtFieldQuizID)) {
            updateMessage("the id can not be empty", Color.RED, txtMessageEditQuiz);
            return false;
        }
        return true;

    }
}
