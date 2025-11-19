package baseNoStates;

import baseNoStates.AreaVisitors.AreaVisitor;

import java.util.ArrayList;
import java.util.Arrays;

// Singleton that contains a tree of all areas and doors.
public final class DirectoryAreas {
  private static Area rootArea;
  private static ArrayList<Door> allDoors;

  // Creates full area tree and array of all doors
  public static void makeAreas() {
    // Creating partitions/spaces
    Partition building = new Partition("building", null);

    Partition basement = new Partition("basement", building);
    Space parking = new Space("parking", basement);

    Partition groundFloor = new Partition("ground_floor", building);
    Space hall = new Space("hall", groundFloor);
    Space room1 = new Space("room1", groundFloor);
    Space room2 = new Space("room2", groundFloor);

    Partition floor1 = new Partition("floor1", building);
    Space room3 = new Space("room3", floor1);
    Space corridor = new Space("corridor", floor1);
    Space it = new Space("IT", floor1);

    Space stairs = new Space("stairs", building);
    Space exterior = new Space("exterior", building);

    // Creating doors
    Door d1 = new Door("D1", exterior, parking);
    Door d2 = new Door("D2", stairs, parking);
    Door d3 = new Door("D3", exterior, hall);
    Door d4 = new Door("D4", stairs, hall);
    Door d5 = new Door("D5", hall, room1);
    Door d6 = new Door("D6", hall, room2);
    Door d7 = new Door("D7", stairs, corridor);
    Door d8 = new Door("D8", corridor, room3);
    Door d9 = new Door("D9", corridor, it);

    rootArea = building;
    allDoors = new ArrayList<>(Arrays.asList(d1, d2, d3, d4, d5, d6, d7, d8, d9));

    Clock.addObservers(allDoors);
  }

  public static Door findDoorById(String id) {
    for (Door allDoor : allDoors) {
      if (allDoor.getId().equals(id)) {
        return allDoor;
      }
    }
    return null;
  }

  public static ArrayList<Door> getAllDoors() {
    return allDoors;
  }

  public static void acceptTreeVisitor(AreaVisitor visitor) {
    rootArea.acceptVisitor(visitor);
  }
}