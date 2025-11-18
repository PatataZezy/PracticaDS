package baseNoStates;

import baseNoStates.AreaVisitors.AreaVisitor;

import java.util.ArrayList;

// Area that contains other areas (ex. building contains basement, ground floor, etc.). Part of
// design pattern Composite to represent the space hierarchy, where any instance of this object will
// never be a leaf.
public class Partition extends Area {
  private ArrayList<Area> subareas;

  public Partition(String id, Partition father) {
    super(id, father);
    this.subareas = new ArrayList<>();
  }

  @Override
  public ArrayList<Door> getDoorsGivingAccess() {
    ArrayList<Door> allDoors = new ArrayList<>();
    for (Area subarea : this.subareas) {
      ArrayList<Door> subareaDoors = subarea.getDoorsGivingAccess();
      allDoors.addAll(subareaDoors);
    }
    return allDoors;
  }

  @Override
  public Area findAreaById(String id) {
    if (this.id.equals(id)) {
      return this;
    }

    for (Area subarea : this.subareas) {
      if (subarea.findAreaById(id) != null) {
        return subarea.findAreaById(id);
      }
    }

    return null;
  }

  @Override
  public ArrayList<Space> getSpaces() {
    ArrayList<Space> allSpaces = new ArrayList<>();
    for (Area subarea : this.subareas) {
      ArrayList<Space> subareaDoors = subarea.getSpaces();
      allSpaces.addAll(subareaDoors);
    }

    return allSpaces;
  }

  public void addArea(Area newArea) {
    this.subareas.add(newArea);
  }

  protected void processVisitor(AreaVisitor visitor) {
    visitor.visitPartition(this);
  }
}
