module quizzey.quizzey {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    exports quizzey.quizzey;
    opens quizzey.quizzey to javafx.fxml;
    opens quizzey.quizzey.StudentPage to javafx.fxml;

    exports quizzey.quizzey.Users;


}