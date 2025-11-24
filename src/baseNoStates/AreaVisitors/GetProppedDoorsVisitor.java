package baseNoStates.AreaVisitors;

import baseNoStates.Area;
import baseNoStates.Door;
import baseNoStates.Partition;
import baseNoStates.Space;
import java.util.ArrayList;

// Visitor for DirectoryAreas where it traverses the entire tree to retrieve all doors under a
// partition with a specific area ID
public class GetProppedDoorsVisitor implements AreaVisitor {
  private ArrayList<Door> doors = null;

  public GetProppedDoorsVisitor() {
  }

  public void visitPartition(Partition partition) {
    for (Area subarea : partition.getSubareas()) {
      subarea.acceptVisitor(this);
    }
  }

  public void visitSpace(Space space) {
    for (Door door : space.getDoorsGivingAccess()) {
      if (door.isPropped()) {
        this.doors.add(door);
      }
    }
  }

  public ArrayList<Door> getResult() {
    return this.doors;
  }
}
