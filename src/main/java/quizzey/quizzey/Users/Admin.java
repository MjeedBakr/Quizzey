package quizzey.quizzey.Users;

import java.util.ArrayList;
import java.util.Objects;

public class Admin extends User{

    public static ArrayList<Admin> adminsList = new ArrayList<Admin>();

    public Admin(String personID, String firstName, String lastName, String email, String password, Role personRole) {
        super(personID, firstName, lastName, email, password, personRole);
    }

    public static void addAdminToList(Admin admin) {
        adminsList.add(admin);
    }

    public Admin() {
    }

    public static Admin getAdminByEmailAndPassword(String email, String password)
    {
        for (Admin adm: adminsList)
        {
            if (Objects.equals(adm.getPassword(), password) && Objects.equals(adm.getEmail(), email))
                return adm;
        }
        return null;
    }

    
    
}
