package baseNoStates;

import java.util.ArrayList;

// In this class we define what an area is and initialize values of the area, the area id, space and
// access to the doors. Part of design pattern Composite to represent the space hierarchy.
public abstract class Area { 
  protected final String id;

  public Area(String id, Partition father) {
    this.id = id;

    if (father != null) {
      father.addArea(this);
    }
  }

  public String getId() {
    return this.id;
  }

  public abstract ArrayList<Door> getDoorsGivingAccess();

  public abstract Area findAreaById(String id);

  public abstract ArrayList<Space> getSpaces();
}