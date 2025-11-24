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

  public void addArea(Area newArea) {
    this.subareas.add(newArea);
  }

  public ArrayList<Area> getSubareas() {
    return this.subareas;
  }

  protected void processVisitor(AreaVisitor visitor) {
    visitor.visitPartition(this);
  }
}
