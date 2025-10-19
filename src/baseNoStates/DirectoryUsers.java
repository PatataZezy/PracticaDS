package baseNoStates;

import java.util.ArrayList;

public final class DirectoryUsers {
  private static final Map<String, User> users = new HashMap<>();
  public static void addUser(User u) {
        if (u != null && u.getId() != null) {
            users.put(u.getId(), u);
        }
    }

    public static User getUser(String id) {
        return users.get(id);
    }

    public static Collection<User> getAllUsers() {
        return users.values();
    }

    public static void buildSample() {
        if (!users.isEmpty()) return;
        addUser(new User("U1", "Alice"));
        addUser(new User("U2", "Bob"));
        addUser(new User("U3", "Charlie"));
    }

  public static void makeUsers() {
    //TODO: make user groups according to the specifications in the comments, because
    // now all are the same

    // users without any privilege, just to keep temporally users instead of deleting them,
    // this is to withdraw all permissions but still to keep user data to give back
    // permissions later
    users.add(new User("Bernat", "12345"));
    users.add(new User("Blai", "77532"));

    // employees :
    // Sep. 1 this year to Mar. 1 next year
    // week days 9-17h
    // just shortly unlock
    // ground floor, floor1, exterior, stairs (this, for all), that is, everywhere but the parking
    users.add(new User("Ernest", "74984"));
    users.add(new User("Eulalia", "43295"));

    // managers :
    // Sep. 1 this year to Mar. 1 next year
    // week days + saturday, 8-20h
    // all actions
    // all spaces
    users.add(new User("Manel", "95783"));
    users.add(new User("Marta", "05827"));

    // admin :
    // always=Jan. 1 this year to 2100
    // all days of the week
    // all actions
    // all spaces
    users.add(new User("Ana", "11343"));
  }

  public static User findUserByCredential(String credential) {
    for (User user : users) {
      if (user.getCredential().equals(credential)) {
        return user;
      }
    }
    System.out.println("user with credential " + credential + " not found");
    return null; // otherwise we get a Java error
  }

}
