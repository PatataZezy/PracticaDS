package baseNoStates;

import baseNoStates.AreaVisitors.AreaVisitor;
import org.json.JSONArray;
import org.json.JSONObject;

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

  public JSONObject toJson(int depth) {
    // for depth=1 only the root and children,
    // for recusive = all levels use Integer.MAX_VALUE
    JSONObject json = new JSONObject();
    json.put("class", "partition");
    json.put("id", id);
    JSONArray jsonAreas = new JSONArray();
    if (depth > 0) {
      for (Area a : this.subareas) {
        jsonAreas.put(a.toJson(depth - 1));
      }
      json.put("areas", jsonAreas);
    }
    return json;
  }
}
