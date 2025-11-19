package baseNoStates.AreaVisitors;

import baseNoStates.*;

import java.util.ArrayList;

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
