package baseNoStates;

import baseNoStates.AreaVisitors.AreaVisitor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

// Area that contains all doors leading to it and no other areas within it. Part of design pattern
// Composite to represent the space hierarchy, where any instance of this object will always be a
// leaf, save for the doors.
public class Space extends Area {
  ArrayList<Door> doorsGivingAccess;

  public Space(String id, Partition father) {
    super(id, father);
    this.doorsGivingAccess = new ArrayList<>();
  }

  public ArrayList<Door> getDoorsGivingAccess() {
    return this.doorsGivingAccess;
  }

  public void addDoor(Door newDoor) {
    this.doorsGivingAccess.add(newDoor);
  }

  protected void processVisitor(AreaVisitor visitor) {
    visitor.visitSpace(this);
  }

  public JSONObject toJson(int depth) { // depth not used here
    JSONObject json = new JSONObject();
    json.put("class", "space");
    json.put("id", id);
    JSONArray jsonDoors = new JSONArray();
    for (Door d : doorsGivingAccess) {
      jsonDoors.put(d.toJson());
    }
    json.put("access_doors", jsonDoors);
    return json;
  }
}