package baseNoStates;

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
  public Door[] getDoorsGivingAccess() {
    // figuring out final array size
    int arraySize = 0;
    for (Area area : this.subareas) {
      arraySize += area.getDoorsGivingAccess().length;
    }

    // creating array and filling
    Door[] allDoors = new Door[arraySize];
    int index = 0;
    for (Area subarea : this.subareas) {
      Door[] subareaDoors = subarea.getDoorsGivingAccess();
      for (Door subareaDoor : subareaDoors) {
        allDoors[index] = subareaDoor;
        index++;
      }
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
  public Space[] getSpaces() {
    // figuring out final array size
    int arraySize = 0;
    for (Area subarea : this.subareas) {
      arraySize += subarea.getSpaces().length;
    }

    // creating array and filling
    Space[] allSpaces = new Space[arraySize];
    int index = 0;
    for (Area subarea : this.subareas) {
      Space[] subareaDoors = subarea.getSpaces();
      for (Space subareaDoor : subareaDoors) {
        allSpaces[index] = subareaDoor;
        index++;
      }
    }

    return allSpaces;
  }

  public void addArea(Area newArea) {
    this.subareas.add(newArea);
  }
}
