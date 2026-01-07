package baseNoStates.requests;

import baseNoStates.*;
import baseNoStates.AreaVisitors.GetAreaByIdVisitor;
import baseNoStates.AreaVisitors.GetDoorsRelatedToSpaceVisitor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class RequestRelatedDoors implements Request {
  private final String areaId;
  private JSONObject json; // 1 level tree, root and children

  public RequestRelatedDoors(String areaId) {
    this.areaId = areaId;
  }

  public String getAreaId() {
    return areaId;
  }

  @Override
  public JSONObject answerToJson() {
    return json;
  }

  @Override
  public String toString() {
    return "RequestChildren{areaId=" + areaId + "}";
  }

  public void process() {
    GetDoorsRelatedToSpaceVisitor getDoorsVisitor = new GetDoorsRelatedToSpaceVisitor(areaId);
    DirectoryAreas.acceptTreeVisitor(getDoorsVisitor);
    Space space = getDoorsVisitor.getResult();

    json = space.toJson(1);
  }
}

