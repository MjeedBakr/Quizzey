package quizzey.quizzey;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import quizzey.quizzey.Quiz.Question;
import quizzey.quizzey.Quiz.Quiz;
import quizzey.quizzey.Quiz.QuizManager;
import quizzey.quizzey.Users.Admin;
import quizzey.quizzey.Users.Student;
import quizzey.quizzey.Users.User;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginPageController {

    public Label labelSelector;
    public ChoiceBox<User.Role> selectorAccount;
    public Label labelEmail;
    public TextField txtFieldEmail;
    public Label labelPassword;
    public PasswordField txtFieldPassword;
    public Button btnLogin;
    public User.Role selectedRule;
    public Label loginMessage;
    public static Student loggedInStudent = new Student();

    public static Admin loggedInAdmin = new Admin();


    public void setRandomData() {
//        Student stu1 = new Student("1", "Ali", "Ahmad", "Ali@Ali.com", "12345678", User.Role.STUDENT);
//        Student stu2 = new Student("2", "Mohammed", "Ahmad", "mohammed@Ali.com", "12345678", User.Role.STUDENT);
        Admin adm1 = new Admin("2", "Mohammed", "Admin", "Admin@Admin.com", "admin123", User.Role.ADMIN);
//        Student.studentsList.add(stu1);
//        Student.studentsList.add(stu2);
        Admin.adminsList.add(adm1);

        //Q1
        HashMap<String, Boolean> question1Hash = new HashMap<>();
        Question question1 = new Question();
        question1Hash.put("Java", true);
        question1Hash.put("C++", false);
        question1Hash.put("Python", false);
        question1Hash.put("Kotlin", false);
        question1.setQuestionID("Q1");
        question1.setTheQuestion("What is the best programming language");
        question1.setChoices(question1Hash);
        //Q2
        HashMap<String, Boolean> question2Hash = new HashMap<>();
        Question question2 = new Question();
        question2Hash.put("HTML", false);
        question2Hash.put("CSS", true);
        question2Hash.put("JavaScript", false);
        question2Hash.put("PHP", false);
        question2.setQuestionID("Q2");
        question2.setTheQuestion("Which language is used for styling web pages?");
        question2.setChoices(question2Hash);
        //Q3
        HashMap<String, Boolean> question3Hash = new HashMap<>();
        Question question3 = new Question();
        question3Hash.put("SQL", false);
        question3Hash.put("NoSQL", true);
        question3Hash.put("MongoDB", false);
        question3Hash.put("Firebase", false);
        question3.setQuestionID("Q3");
        question3.setTheQuestion("Which type of database is used for unstructured data?");
        question3.setChoices(question3Hash);
        //Q4
        HashMap<String, Boolean> question4Hash = new HashMap<>();
        Question question4 = new Question();
        question4Hash.put("Linux", true);
        question4Hash.put("Windows", false);
        question4Hash.put("MacOS", false);
        question4Hash.put("Android", false);
        question4.setQuestionID("Q4");
        question4.setTheQuestion("Which operating system is open source?");
        question4.setChoices(question4Hash);
        //Q5
        HashMap<String, Boolean> question5Hash = new HashMap<>();
        Question question5 = new Question();
        question5Hash.put("Machine Learning", true);
        question5Hash.put("Blockchain", false);
        question5Hash.put("IoT", false);
        question5Hash.put("Virtual Reality", false);
        question5.setQuestionID("Q5");
        question5.setTheQuestion("Which technology is used for making computers learn without being explicitly programmed?");
        question5.setChoices(question5Hash);

        ArrayList<Question> questionsList1 = new ArrayList<>();
        questionsList1.add(question1);
        questionsList1.add(question2);
        questionsList1.add(question3);
        questionsList1.add(question4);
        questionsList1.add(question5);

        Quiz quiz1 = new Quiz(QuizManager.generateQuizID(), "Advanced Coding", (short) 30, "spl3mf", (short) 5, questionsList1);
        QuizManager.quizzesList.add(quiz1);



        QuizManager.displayQuizzes();
    }




    public void initialize() {

        setRandomData();
        // Add roles to the ChoiceBox
        selectorAccount.getItems().addAll(User.Role.STUDENT, User.Role.ADMIN);

        // Set Admin as a default role
        selectorAccount.setValue(selectorAccount.getItems().get(1));

        // Add a listener to the ChoiceBox to handle selection changes
        selectorAccount.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Update label or perform other actions based on the selected role
                labelSelector.setText("Selected Role: " + newValue);
                selectedRule = newValue;
            }
        });

        // focus on password field when press enter key in email field
        txtFieldEmail.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                txtFieldPassword.requestFocus();
            }
        });

        //Login when press enter
        txtFieldPassword.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                handleLoginButtonAction();
            }
        });

        btnLogin.setOnMouseClicked(mouseEvent -> handleLoginButtonAction());

    }

    private void handleLoginButtonAction() {
        // Implement login functionality based on the selected role
        User.Role selectedRole = selectorAccount.getValue();
        String email = txtFieldEmail.getText();
        String password = txtFieldPassword.getText();

        if (email.isEmpty()) {
            loginMessage.setText("Please enter your email ");
            return;
        }
        if (password.isEmpty()) {
            loginMessage.setText("Please enter a password");
            return;
        }

        boolean loginSuccessful = performLogin(selectedRole, email, password);


        if (loginSuccessful) {
            // Successful login logic
            loginMessage.setText("Login Successful");
            loginMessage.setStyle("-fx-text-fill: green; -fx-font-weight: bold");  // Set text color to green
        } else {
            // Failed login logic
            loginMessage.setText("Login Failed");
            loginMessage.setStyle("-fx-text-fill: red; -fx-font-weight: bold");  // Set text color to red
        }
    }

    private boolean performLogin(User.Role role, String email, String password) {
        // login logic here with database and regular expressions and return true if successful, false otherwise
//        Student test = dbLogin();
        System.out.println("enter perform login");


        switch (role) {
            case ADMIN -> {
                loggedInAdmin = Admin.getAdminByEmailAndPassword(email, password);
                // Authenticate admin
                // Add admin authentication logic here
                if (loggedInAdmin != null) {
                    openAdminPage(loggedInAdmin);
                    return true;
                }
                return false;
            }
            case STUDENT -> {
                loggedInStudent = dbLogin();
                // Authenticate student
                // Add student authentication logic here

                if (loggedInStudent != null) {
                    openStudentPage(loggedInStudent);
                    return true; // Login successful
                }
                return false;
            }

        }

        return false;
    }




    public Student dbLogin(){
        System.out.println("Enter Login");
        DataBase dbc = new DataBase();
        Connection connectdb = dbc.getConnection();
        String email = txtFieldEmail.getText();
        String password = txtFieldPassword.getText();
        int id;
        String name;
        Student stu = null;

        String valid = "select count(1) From students where email ='" +email+ "' AND password ='"+ password+ "'";
        // "select count(1) From students where email = ' "+  email  +"' AND password = ' "+ password +"' ;";
        String selected = "select student_id,name,email,password from students where email = '"+email+"' AND password ='"+password+"'";

        try {
            Statement statement = connectdb.createStatement();
            ResultSet queryResult = statement.executeQuery(valid);

            while(queryResult.next()){
                if(queryResult.getInt(1)==1){
                    ResultSet selectQuery =statement.executeQuery(selected);
                    while(selectQuery.next()) {

                        id = selectQuery.getInt(1);
                        name = selectQuery.getString(2);
                        email = selectQuery.getString(3);
                        password = selectQuery.getString(4);
                        stu = new Student(Integer.toString(id),name,name,email,password);
                    }
                    System.out.println(stu.getPersonID()+ " " + stu.getFirstName()+ " " + stu.getEmail()+ " " + stu.getPassword());
                    System.out.println("Logged in");
                    return stu;
                }
                else{
                    System.out.println("not logged in");
                    return stu;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
    private void openAdminPage(Admin loggedInAdmin) {
        try {
            // Load the FXML file for the Admin page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("AdminPageStyles/AdminPage.fxml"));

            // Create a new scene
            Scene adminScene = new Scene(loader.load());

            // Get the controller from the loader
            AdminPageController adminPageController = loader.getController();


            // Get the stage from the current scene
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();

            // Set the new scene on the stage
            currentStage.setScene(adminScene);

            // Show the stage
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openStudentPage(Student student) {
        try {
            // Load the FXML file for the student page
            FXMLLoader loader = new FXMLLoader(getClass().getResource("StudentPageStyles/StudentPage.fxml"));

            // Create a new scene
            Scene studentScene = new Scene(loader.load());

            // Get the controller from the loader
            StudentPageController studentPageController = loader.getController();


            // Get the stage from the current scene
            Stage currentStage = (Stage) btnLogin.getScene().getWindow();

            // Set the new scene on the stage
            currentStage.setScene(studentScene);

            // Show the stage
            currentStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
