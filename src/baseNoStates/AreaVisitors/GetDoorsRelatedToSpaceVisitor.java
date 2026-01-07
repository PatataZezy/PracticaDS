package baseNoStates.AreaVisitors;

import baseNoStates.Area;
import baseNoStates.Door;
import baseNoStates.Partition;
import baseNoStates.Space;

import java.util.ArrayList;

public class GetDoorsRelatedToSpaceVisitor implements AreaVisitor {
  private Space space;
  private final String spaceId;

  public GetDoorsRelatedToSpaceVisitor(String spaceId) {
    this.space = new Space(spaceId, null);
    this.spaceId = spaceId;
  }

  public void visitPartition(Partition partition) {
    for (Area subarea : partition.getSubareas()) {
      subarea.acceptVisitor(this);
    }
  }

  public void visitSpace(Space space) {
    for (Door door : space.getDoorsGivingAccess()) {
      if (door.getSpaceComingFrom().getId().equals(this.spaceId)
          || door.getSpaceLeadingTo().getId().equals(this.spaceId)) {
        this.space.addDoor(door);
      }
    }
  }

  public Space getResult() {
    return this.space;
  }
}
