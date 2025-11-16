package baseNoStates;

import java.util.ArrayList;

// Area that contains all doors leading to it and no other areas within it
public class Space extends Area {
  ArrayList<Door> doorsGivingAccess;

  Space(String id, Partition father) {
    super(id, father);
    this.doorsGivingAccess = new ArrayList<>();
  }

  @Override
  public Door[] getDoorsGivingAccess() {
    Door[] doorArray = new Door[this.doorsGivingAccess.size()];
    for (int i = 0; i < this.doorsGivingAccess.size(); i++) {
      doorArray[i] = this.doorsGivingAccess.get(i);
    }
    return doorArray;
  }

  @Override
  public Area findAreaById(String id) {
    return (this.id.equals(id)) ? this : null;
  }

  @Override
  public Space[] getSpaces() {
    Space[] thisSpace = new Space[1];
    thisSpace[0] = this;
    return thisSpace;
  }

  public void addDoor(Door newDoor) {
    this.doorsGivingAccess.add(newDoor);
  }
}
