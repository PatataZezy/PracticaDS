package baseNoStates;

import baseNoStates.AreaVisitors.AreaVisitor;

import java.util.ArrayList;
import java.util.List;

// Area that contains all doors leading to it and no other areas within it. Part of design pattern
// Composite to represent the space hierarchy, where any instance of this object will always be a
// leaf, save for the doors.
public class Space extends Area {
  ArrayList<Door> doorsGivingAccess;

  Space(String id, Partition father) {
    super(id, father);
    this.doorsGivingAccess = new ArrayList<>();
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {
    return this.doorsGivingAccess;
  }

  @Override
  public Area findAreaById(String id) {
    return (this.id.equals(id)) ? this : null;
  }

  @Override
  public ArrayList<Space> getSpaces() {
    return new ArrayList<>(List.of(this));
  }

  public void addDoor(Door newDoor) {
    this.doorsGivingAccess.add(newDoor);
  }

  protected void processVisitor(AreaVisitor visitor) {
    visitor.visitSpace(this);
  }
}
