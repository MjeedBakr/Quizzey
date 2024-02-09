module quizzey.quizzey {
    requires javafx.media;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    exports quizzey.quizzey;
    opens quizzey.quizzey to javafx.fxml;

    exports quizzey.quizzey.Users;
    exports quizzey.quizzey.Quiz;

}