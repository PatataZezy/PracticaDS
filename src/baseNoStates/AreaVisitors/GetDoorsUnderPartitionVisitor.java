package baseNoStates.AreaVisitors;

import baseNoStates.Area;
import baseNoStates.Door;
import baseNoStates.Partition;
import baseNoStates.Space;
import java.util.ArrayList;

// Visitor for DirectoryAreas where it traverses the entire tree to retrieve all doors under a
// partition with a specific area ID
public class GetDoorsUnderPartitionVisitor implements AreaVisitor {
  private ArrayList<Door> doors = null;
  private final String searchedId;

  public GetDoorsUnderPartitionVisitor(String searchedId) {
    this.searchedId = searchedId;
  }

  public void visitPartition(Partition partition) {
    for (Area subarea : partition.getSubareas()) {
      subarea.acceptVisitor(this);
    }
  }

  public void visitSpace(Space space) {
    if (space.getId().equals(this.searchedId)) {
      this.doors = space.getDoorsGivingAccess();
    }
  }

  public ArrayList<Door> getResult() {
    return this.doors;
  }
}