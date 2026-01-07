import 'package:flutter/material.dart';
import 'package:doors_flutter/tree.dart';
import 'package:doors_flutter/requests.dart';
import 'package:doors_flutter/screen_space.dart';

class ScreenPartition extends StatefulWidget {
  final String id;
  
  const ScreenPartition({super.key, this.id = 'ROOT'});

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
        title: Text(_formatName(widget.id)),
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
        leading: _getPartitionIcon(area.id),
        title: Text(_formatName(area.id), style: const TextStyle(fontSize: 18)),
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
        title: Text(_formatName(area.id), style: const TextStyle(fontSize: 18)),
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

  Widget _getPartitionIcon(String partitionId) {
    // Mostrar indicadores de planta para las partitions principales
    String label;
    Color backgroundColor;
    
    switch (partitionId) {
      case 'basement':
        label = 'P-1';
        backgroundColor = Colors.brown.shade700;
        break;
      case 'ground_floor':
        label = 'P0';
        backgroundColor = Colors.blue.shade700;
        break;
      case 'floor1':
        label = 'P1';
        backgroundColor = Colors.purple.shade700;
        break;
      default:
        // Para otras partitions, usar icono de carpeta
        return const Icon(Icons.folder, color: Colors.blue, size: 32);
    }
    
    return Container(
      width: 40,
      height: 40,
      decoration: BoxDecoration(
        color: backgroundColor,
        borderRadius: BorderRadius.circular(8),
      ),
      child: Center(
        child: Text(
          label,
          style: const TextStyle(
            color: Colors.white,
            fontSize: 14,
            fontWeight: FontWeight.bold,
          ),
        ),
      ),
    );
  }

  String _formatName(String string) {
    if (string == "ROOT") {
      return "[Root]";
    }

    if (string == "IT") {
      return "IT";
    }

    String result = string.replaceAll(RegExp(r"_"), " ");

    result = result.replaceAll(RegExp(r"floor"), "floor ");
    result = result.replaceAll(RegExp(r"room"), "room ");

    result = "${result[0].toUpperCase()}${result.substring(1).toLowerCase()}";
    return result;
  }
}
