package baseNoStates;

import baseNoStates.Privileges.NoPrivileges;
import baseNoStates.Privileges.PrivilegesAdmin;
import baseNoStates.Privileges.PrivilegesEmployee;
import baseNoStates.Privileges.PrivilegesManager;

// Static class containing all user groups and its users
public final class DirectoryUserGroups {
    private static UserGroup[] userGroups;

    // Creates all user groups
    public static void makeUserGroups() {
        // Without privileges
        User bernat = new User("Bernat", "12345");
        User blai = new User("Blai", "77532");
        UserGroup noPrivileges = new UserGroup(new User[]{bernat, blai}, new NoPrivileges());
        bernat.setUserGroup(noPrivileges);
        blai.setUserGroup(noPrivileges);


        // Employees
        User ernest = new User("Ernest", "74984");
        User eulalia = new User("Eulalia", "43295");
        UserGroup employees = new UserGroup(new User[]{ernest, eulalia}, new PrivilegesEmployee());
        ernest.setUserGroup(employees);
        eulalia.setUserGroup(employees);

        // Managers
        User manel = new User("Manel", "95783");
        User marta = new User("Marta", "05827");
        UserGroup managers = new UserGroup(new User[]{manel, marta}, new PrivilegesManager());
        manel.setUserGroup(managers);
        marta.setUserGroup(managers);

        // Admin
        User ana = new User("Ana", "11343");
        UserGroup admin = new UserGroup(new User[]{ana}, new PrivilegesAdmin());
        ana.setUserGroup(admin);

        userGroups = new UserGroup[]{noPrivileges, employees, managers, admin};
    }

    public static User findUserByCredential(String credential) {
        for (UserGroup userGroup : userGroups) {
            if (userGroup.findUserByCredential(credential) != null) {
                return userGroup.findUserByCredential(credential);
            }
        }
        return null;
    }
}
