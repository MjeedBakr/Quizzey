package quizzey.quizzey.Users;

import java.util.regex.Pattern;

public class User {
    
    public enum Role {STUDENT, ADMIN};

    private String personID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role personRole;

        // Constructor 1 - Default Constructor
        public User() {
        }
    
        // Constructor 2 - Parameterized Constructor
        public User(String personID, String firstName, String lastName, String email, String password, Role personRole) {
            this.personID = personID;
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
            this.password = password;
            this.personRole = personRole;
        }
    
        // Getter and Setter for personID
        public String getPersonID() {
            return personID;
        }
    
        public void setPersonID(String personID) {
            this.personID = personID;
        }
    
        // Getter and Setter for firstName
        public String getFirstName() {
            return firstName;
        }
    
        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }
    
        // Getter and Setter for lastName
        public String getLastName() {
            return lastName;
        }
    
        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    
        // Getter and Setter for email
        public String getEmail() {
            return email;
        }
    
        public void setEmail(String email) {
            if (isValidEmail(email)) {
                this.email = email;
            } else {
                throw new IllegalArgumentException("Invalid email address");
            }
        }
    
        // Getter and Setter for password
        public String getPassword() {
            return password;
        }
    
        public void setPassword(String password) {
            if (isValidPassword(password)) {
                this.password = password;
            } else {
                throw new IllegalArgumentException("Invalid password");
            }
        }
    
        // Getter and Setter for personRole
        public Role getPersonRole() {
            return personRole;
        }

        public String getStringPersonRule()
        {
            return getPersonRole().toString();
        }
    
        public void setPersonRole(Role personRole) {
            this.personRole = personRole;
        }

        //Validation Methods
        public static boolean isValidEmail(String email)
        {
            String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            return email != null && Pattern.matches(regexPattern, email);
        }

        public static boolean isValidPassword(String password)
        {
            return password != null && password.length() >= 8;
        }

}
