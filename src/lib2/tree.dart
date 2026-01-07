// Models for the tree structure

abstract class Area {
  late String id;
  late List<dynamic> children;
  Area(this.id, this.children);
}

class Partition extends Area {
  Partition(String id, List<Area> children) : super(id, children);
}

class Space extends Area {
  Space(String id, List<Door> children) : super(id, children);
}

class Door {
  late String id;
  late bool closed;
  late String state;
  
  Door({required this.id, this.state = "unlocked", this.closed = true});
  
  Door.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    closed = json['closed'];
    state = json['state'];
  }
}

class Tree {
  late Area root;
  
  Tree(this.root);
  
  Tree.fromJson(Map<String, dynamic> json) {
    String classType = json['class'];
    
    if (classType == 'partition') {
      // It's a partition with areas as children
      List<dynamic> areasJson = json['areas'] ?? [];
      List<Area> areas = [];
      
      for (var areaJson in areasJson) {
        String areaClass = areaJson['class'];
        if (areaClass == 'partition') {
          areas.add(Partition(areaJson['id'], [])); // Simplified, children loaded on demand
        } else if (areaClass == 'space') {
          areas.add(Space(areaJson['id'], [])); // Simplified, children loaded on demand
        }
      }
      
      root = Partition(json['id'], areas);
    } else if (classType == 'space') {
      // It's a space with doors as children
      List<dynamic> doorsJson = json['accessdoors'] ?? [];
      List<Door> doors = doorsJson.map((d) => Door.fromJson(d)).toList();
      root = Space(json['id'], doors);
    }
  }
}
