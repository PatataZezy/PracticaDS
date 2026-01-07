import 'package:flutter/material.dart';
import 'package:tutorial_acs/tree.dart';
import 'package:tutorial_acs/requests.dart';
import 'package:tutorial_acs/screen_space.dart';

class ScreenPartition extends StatefulWidget {
  final String id;
  
  const ScreenPartition({super.key, this.id = 'building'});

  @override
  State<ScreenPartition> createState() => _ScreenPartitionState();
}

class _ScreenPartitionState extends State<ScreenPartition> {
  late Future<Tree> futureTree;

  @override
  void initState() {
    super.initState();
    futureTree = getTree(widget.id);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.primary,
        foregroundColor: Theme.of(context).colorScheme.onPrimary,
        title: Text(widget.id),
        actions: <Widget>[
          IconButton(
            icon: const Icon(Icons.home),
            onPressed: () {
              Navigator.of(context).pushAndRemoveUntil(
                MaterialPageRoute(
                  builder: (context) => const ScreenPartition(id: 'building'),
                ),
                (route) => false,
              );
            },
          ),
        ],
      ),
      body: FutureBuilder<Tree>(
        future: futureTree,
        builder: (context, snapshot) {
          if (snapshot.hasError) {
            return Center(child: Text('Error: ${snapshot.error}'));
          }
          
          if (snapshot.connectionState == ConnectionState.waiting) {
            return const Center(child: CircularProgressIndicator());
          }
          
          if (!snapshot.hasData) {
            return const Center(child: Text('No data'));
          }

          return ListView.separated(
            padding: const EdgeInsets.all(16.0),
            itemCount: snapshot.data!.root.children.length,
            itemBuilder: (BuildContext context, int index) =>
                _buildRow(snapshot.data!.root.children[index], index),
            separatorBuilder: (BuildContext context, int index) =>
                const Divider(),
          );
        },
      ),
    );
  }

  Widget _buildRow(Area area, int index) {
    assert(area is Partition || area is Space);
    
    if (area is Partition) {
      return ListTile(
        leading: const Icon(Icons.folder, color: Colors.blue, size: 32),
        title: Text(area.id, style: const TextStyle(fontSize: 18)),
        trailing: const Icon(Icons.arrow_forward_ios),
        onTap: () {
          Navigator.of(context).push(
            MaterialPageRoute(
              builder: (context) => ScreenPartition(id: area.id),
            ),
          );
        },
      );
    } else {
      return ListTile(
        leading: const Icon(Icons.meeting_room, color: Colors.green, size: 32),
        title: Text(area.id, style: const TextStyle(fontSize: 18)),
        trailing: const Icon(Icons.arrow_forward_ios),
        onTap: () {
          Navigator.of(context).push(
            MaterialPageRoute(
              builder: (context) => ScreenSpace(id: area.id),
            ),
          );
        },
      );
    }
  }
}
