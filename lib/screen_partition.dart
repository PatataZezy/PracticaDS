import 'package:flutter/material.dart';
import 'package:doors_flutter/translations.dart';
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
          PopupMenuButton<String>(
            icon: const Icon(Icons.language),
            initialValue: language,
            onSelected: (String newLanguage) {
              language = newLanguage;
              setState(() {
                futureTree = getTree(widget.id);
              });
            },
            itemBuilder: (BuildContext context) => <PopupMenuEntry<String>>[
              const PopupMenuItem<String>(value: "Català", child: Text("Català")),
              const PopupMenuItem<String>(value: "Castellano", child: Text("Castellano")),
              const PopupMenuItem<String>(value: "English", child: Text("English")),
            ],
          ),
          IconButton(
            icon: const Icon(Icons.home),
            onPressed: () {
              Navigator.of(context).pushAndRemoveUntil(
                MaterialPageRoute(
                  builder: (context) => const ScreenPartition(id: 'ROOT'),
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
      persistentFooterButtons: [
        ElevatedButton.icon(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.red,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
          ),
          onPressed: () {
            lockAllUnderArea(widget.id);
          },
          icon: const Icon(Icons.lock, size: 18),
          label: Text(translate('Lock all doors here')),
        ),
        ElevatedButton.icon(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.blue,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
          ),
          onPressed: () {
            unlockAllUnderArea(widget.id);
          },
          icon: const Icon(Icons.lock_open, size: 18),
          label: Text(translate('Unlock all doors here')),
        ),
      ],
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
        leading: _getSpaceIcon(area.id),
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
        label = '-1';
        backgroundColor = Colors.brown.shade700;
        break;
      case 'ground_floor':
        label = '0';
        backgroundColor = Colors.blue.shade700;
        break;
      case 'floor1':
        label = '1';
        backgroundColor = Colors.purple.shade700;
        break;
      case "building":
        return const Icon(Icons.location_city, color: Colors.blue, size: 32);
      default:
        // Para otras partitions, usar icono de carpeta
        return const Icon(Icons.folder, color: Colors.blue, size: 32);
    }
    
    return Container(
      width: 40,
      height: 40,
      decoration: BoxDecoration(
        color: Colors.blue,  // backgroundColor,
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

  Widget _getSpaceIcon(String spaceId) {
    switch (spaceId) {
      case "stairs":
        return const Icon(Icons.stairs, color: Colors.green, size: 32);
      case "exterior":
        return const Icon(Icons.park, color: Colors.green, size: 32);
      case "parking":
        return const Icon(Icons.fire_truck, color: Colors.green, size: 32);
      case "room1":
      case "room2":
      case "room3":
        return const Icon(Icons.space_dashboard, color: Colors.green, size: 32);
      case "IT":
        return const Icon(Icons.computer, color: Colors.green, size: 32);
      default:
        return const Icon(Icons.meeting_room, color: Colors.green, size: 32);
    }
  }

  String _formatName(String string) {
    if (string == "ROOT") {
      return translate("ACS System");
    }

    if (string == "IT") {
      return translate("IT");
    }

    String result = string.replaceAll(RegExp(r"_"), " ");

    result = result.replaceAll(RegExp(r"floor"), "floor ");
    result = result.replaceAll(RegExp(r"room"), "room ");

    result = "${result[0].toUpperCase()}${result.substring(1).toLowerCase()}";
    return translate(result);
  }
}
