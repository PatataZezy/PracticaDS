package baseNoStates.AreaVisitors;

import baseNoStates.Area;
import baseNoStates.Partition;
import baseNoStates.Space;
import java.util.ArrayList;

// Visitor for DirectoryAreas where it traverses the entire tree to retrieve all spaces under an
// area with a specific area ID
public class GetSpacesInAreaVisitor implements AreaVisitor {
  private ArrayList<Space> spaces = new ArrayList<>();
  private final String searchedId;

  // Used to know if the visitor is under the desired area, even if not in the area itself
  private boolean isInDesiredArea = false;

  public GetSpacesInAreaVisitor(String searchedId) {
    this.searchedId = searchedId;
  }

  public void visitPartition(Partition partition) {
    if (partition.getId().equals(this.searchedId)) {
      this.isInDesiredArea = true;
    }
    for (Area subarea : partition.getSubareas()) {
      subarea.acceptVisitor(this);
    }
    if (partition.getId().equals(this.searchedId)) {
      this.isInDesiredArea = false;
    }
  }

  public void visitSpace(Space space) {
    if (space.getId().equals(this.searchedId) || this.isInDesiredArea) {
      this.spaces.add(space);
    }
  }

  public ArrayList<Space> getResult() {
    return this.spaces;
  }
}
