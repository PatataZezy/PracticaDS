package baseNoStates.AreaVisitors;

import baseNoStates.Area;
import baseNoStates.Door;
import baseNoStates.Partition;
import baseNoStates.Space;

import java.util.ArrayList;

public class GetDoorsRelatedToSpaceVisitor implements AreaVisitor {
  private ArrayList<Door> doors;
  private final String spaceId;

  public GetDoorsRelatedToSpaceVisitor(String spaceId) {
    this.doors = new ArrayList<>();
    this.spaceId = spaceId;
  }

  public void visitPartition(Partition partition) {
    for (Area subarea : partition.getSubareas()) {
      subarea.acceptVisitor(this);
    }
  }

  public void visitSpace(Space space) {
    for (Door door : space.getDoorsGivingAccess()) {
      if (door.getSpaceComingFrom().equals(this.spaceId)
          || door.getSpaceLeadingTo().equals(this.spaceId)) {
        this.doors.add(door);
      }
    }
  }

  public ArrayList<Door> getResult() {
    return this.doors;
  }
}
