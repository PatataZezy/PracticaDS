import 'package:flutter/material.dart';
import 'package:tutorial_acs/tree.dart';

class ScreenPartition extends StatefulWidget {
  const ScreenPartition({super.key});

  @override
  State<ScreenPartition> createState() => _ScreenPartitionState();
}

class _ScreenPartitionState extends State<ScreenPartition> {
  late Tree tree;

  @override
  void initState() {
    super.initState();
    tree = getTree("building");
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(tree.root.id),
        actions: <Widget>[
          IconButton(icon: const Icon(Icons.home),
            onPressed: () {}
          // TODO go home page = root
        ),
        //TODO other actions
      ],
    ),
    body: ListView.separated(
      // it's like ListView.builder() but better
      // because it includes a separator between items
      padding: const EdgeInsets.all(16.0),
      itemCount: tree.root.children.length,
      itemBuilder: (BuildContext context, int index) =>
          _buildRow(tree.root.children[index], index),
      separatorBuilder: (BuildContext context, int index) =>
      const Divider(),
    ),
  );
}
}

Widget _buildRow(Area area, int index) {
  assert (area is Partition || area is Space);
  if (area is Partition) {
    return ListTile(
      title: Text('P ${area.id}'),
      onTap: () => {},
      // TODO, navigate down to show children areas
    );
  } else {
    return ListTile(
      title: Text('S ${area.id}'),
      onTap: () => {},
      // TODO, navigate down to show children doors
    );
  }
}

abstract class Area{
  late String id;
  late List<dynamic> children;
  Area(this.id, this.children);
}

class Partition extends Area {
  Partition(String id, List<Area>children) : super(id, children);
}

class Space extends Area {
  Space(String id, List<Door> children) : super(id, children);
}

class Door {
  late String id;
  late bool closed;
  late String state;
  Door({required this.id, this.state="unlocked", this.closed=true});
}

// at the moment this class seems unnecessary but later we will extend it
class Tree {
  late Area root;

  Tree(this.root);
}

Tree getTree(String id) {
  final List<Door> doors = List<Door>.of([
    Door(id:"D1"), Door(id:"D2"), Door(id:"D3"), Door(id:"D4"), Door(id:"D5"),
    Door(id:"D6"), Door(id:"D7"), Door(id:"D8"), Door(id:"D9")
  ]);

  Map<String, Area> areas = {};
  areas["parking"] = Space("parking", List<Door>.of([doors[0], doors[1]]));
  areas["room1"] = Space("room1", List<Door>.of([doors[4]]));
  areas["room2"] = Space("room2", List<Door>.of([doors[5]]));
  areas["hall"] = Space("hall", List<Door>.of([doors[2], doors[3]]));
  areas["room3"] = Space("room3", List<Door>.of([doors[7]]));
  areas["it"] = Space("it", List<Door>.of([doors[8]]));
  areas["corridor"] = Space("corridor", List<Door>.of([doors[6]]));
  areas["basement"] = Partition("basement", List<Area>.of([areas["parking"]!]));
  areas["ground_floor"] = Partition("ground_floor",
      List<Area>.of([areas["room1"]!, areas["room2"]!, areas["hall"]!
      ]));
  areas["floor1"] = Partition("floor1",
      List<Area>.of([areas["room3"]!, areas["it"]!, areas["corridor"]!
      ]));
  areas["building"] = Partition("building",
      List<Area>.of([areas["basement"]!, areas["ground_floor"]!, areas["floor1"]!
      ]));

  return Tree(areas[id]!);
}

testGetTree() {
  Tree tree;

  for (String id in ["building", "hall", "floor1", "room1"]) {
    tree = getTree(id);
    if (tree.root is Partition) {
      print("root ${tree.root.id}");
      for (Area area in tree.root.children) {
        print("child ${area.id}");
      }
    } else {
      print("root ${tree.root.id}");
      for (Door door in tree.root.children) {
        print("child ${door.id}, state ${door.state}, closed ${door.closed}");
      }
    }
    print("");
  }
}

void main() {
  testGetTree();
}