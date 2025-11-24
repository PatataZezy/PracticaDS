package baseNoStates.AreaVisitors;

import baseNoStates.Area;
import baseNoStates.Partition;
import baseNoStates.Space;

// Visitor for DirectoryAreas where it traverses the entire tree to retrieve an area by its area ID
public class GetAreaByIdVisitor implements AreaVisitor {
  private Area area = null;
  private final String searchedId;

  public GetAreaByIdVisitor(String searchedId) {
    this.searchedId = searchedId;
  }

  public void visitPartition(Partition partition) {
    if (partition.getId().equals(this.searchedId)) {
      this.area = partition;
    }
    if (this.area != null) {
      for (Area subarea : partition.getSubareas()) {
        subarea.acceptVisitor(this);
      }
    }
  }

  public void visitSpace(Space space) {
    if (space.getId().equals(this.searchedId)) {
      this.area = space;
    }
  }

  public Area getResult() {
    return this.area;
  }
}
