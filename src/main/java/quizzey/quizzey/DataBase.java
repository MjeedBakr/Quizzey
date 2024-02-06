package quizzey.quizzey;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Driver;

public class DataBase {
    
    public Connection dataBaseLink;

    public Connection getConnection(){
        String dataBaseName = "quizzey";
        String dataBaseUser = "root";
        String dataBasePassword = "0159";
        String url = "jdbc:mysql://localhost/"+dataBaseName;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            dataBaseLink = DriverManager.getConnection(url,dataBaseUser,dataBasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataBaseLink;
    }
}
