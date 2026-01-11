import "dart:async";
import 'package:doors_flutter/screen_partition.dart';
import 'package:flutter/material.dart';
import 'package:doors_flutter/tree.dart';
import 'package:doors_flutter/requests.dart';

class ScreenSpace extends StatefulWidget {
  final String id;

  const ScreenSpace({super.key, required this.id});

  @override
  State<ScreenSpace> createState() => _ScreenSpaceState();
}

class _ScreenSpaceState extends State<ScreenSpace> {
  late Future<Tree> futureTree;

  late Timer _timer;
  static const int periodRefresh = 1;

  @override
  void initState() {
    super.initState();
    futureTree = getDoorsRelated(widget.id);
    _activateTimer();
  }

  void _activateTimer() {
    _timer = Timer.periodic(Duration(seconds: periodRefresh), (Timer t) {
      _refresh();
    });
  }

  @override
  void dispose() {
    // "The framework calls this method when this State object will never build again"
    // therefore when going up
    _timer.cancel();
    super.dispose();
  }

  void _refresh() {
    setState(() {
      futureTree = getDoorsRelated(widget.id);
    });
  }

  @override
  Widget build(BuildContext context) {
    final screenSize = MediaQuery.of(context).size;

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

          if (!snapshot.hasData) {
            return const Center(child: CircularProgressIndicator());
          }

          List<Door> doors = snapshot.data!.root.children.cast<Door>();

          return Stack(
            children: [
              ListView.separated(
                padding: const EdgeInsets.all(16.0),
                itemCount: doors.length,
                itemBuilder: (BuildContext context, int i) =>
                    _buildRow(doors[i], i),
                separatorBuilder: (BuildContext context, int index) =>
                    const Divider(),
              ),
              snapshot.connectionState != ConnectionState.done
                  ? Positioned(
                      top: screenSize.height * 0.3,
                      left: screenSize.width * 0.4,
                      child: Container(
                        height: screenSize.height / 4,
                        width: screenSize.width / 4,
                        child: const Center(
                          child: CircularProgressIndicator(),
                        ),
                      ),
                    )
                  : Container(),
            ],
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
          label: const Text('Lock all doors here'),
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
          label: const Text('Unlock all doors here'),
        ),
      ],
    );
  }

  Widget _buildRow(Door door, int index) {
    return Card(
      elevation: 2,
      margin: const EdgeInsets.symmetric(vertical: 8),
      child: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            // Door title with icon
            Row(
              children: [
                Icon(
                  door.closed ? Icons.door_front_door : Icons.door_front_door_outlined,
                  color: _getStateColor(door.state),
                  size: 32,
                ),
                const SizedBox(width: 12),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        _getDoorName(door),
                        style: const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        'Current state: ${_formatName(door.state)}',
                        style: TextStyle(
                          fontSize: 14,
                          color: Colors.grey[600],
                        ),
                      ),
                      Text(
                        door.closed ? 'Closed' : 'Opened',
                        style: TextStyle(
                          fontSize: 14,
                          color: Colors.grey[600],
                        ),
                      ),
                    ],
                  ),
                ),
              ],
            ),
            const SizedBox(height: 16),
            // Action buttons (Figma style)
            Wrap(
              spacing: 8,
              runSpacing: 8,
              children: _buildActionButtons(door),
            ),
          ],
        ),
      ),
    );
  }

  List<Widget> _buildActionButtons(Door door) {
    List<Widget> buttons = [];

    // Open/Close button based on door.closed
    if (door.closed) {
      // Puerta cerrada, mostrar botón Open
      buttons.add(
        ElevatedButton.icon(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.green,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
          ),
          onPressed: door.state == 'locked' ? null : () {
            // Solo se puede abrir si está unlocked
            openDoor(door);
            _refresh();
          },
          icon: const Icon(Icons.door_sliding, size: 18),
          label: const Text('Open'),
        ),
      );
    } else {
      // Puerta abierta, mostrar botón Close
      buttons.add(
        ElevatedButton.icon(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.orange,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
          ),
          onPressed: () {
            closeDoor(door);
            _refresh();
          },
          icon: const Icon(Icons.door_front_door, size: 18),
          label: const Text('Close'),
        ),
      );
    }

    // Lock/Unlock button based on state
    if (door.state == 'locked') {
      buttons.add(
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
            unlockDoor(door);
            _refresh();
          },
          icon: const Icon(Icons.lock_open, size: 18),
          label: const Text('Unlock'),
        ),
      );
    } else {
      buttons.add(
        ElevatedButton.icon(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.red,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
          ),
          onPressed: (door.closed && (door.state == "unlocked")) ? () {
            // Solo se puede bloquear si está cerrada
            lockDoor(door);
            _refresh();
          } : null,
          icon: const Icon(Icons.lock, size: 18),
          label: const Text('Lock'),
        ),
      );
    }

    buttons.add(
      ElevatedButton.icon(
        style: ElevatedButton.styleFrom(
          backgroundColor: Colors.lightGreenAccent,
          foregroundColor: Colors.white,
          padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
          shape: RoundedRectangleBorder(
            borderRadius: BorderRadius.circular(20),
          ),
        ),
        onPressed: (door.state == "locked") ? () {
          unlockDoorShortly(door);
          _refresh();
        } : null,
        icon: const Icon(Icons.timer, size: 18),
        label: const Text('Unlock shortly'),
      ),
    );

    return buttons;
  }

  Color _getStateColor(String state) {
    switch (state) {
      case 'locked':
        return Colors.orange;
      case 'unlocked':
        return Colors.green;
      case 'unlocked_shortly':
        return Colors.lightGreenAccent;
      case 'propped':
        return Colors.red;
      default:
        return Colors.grey;
    }
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

  String _getDoorName(Door door) {
    if (door.spaceComingFrom == widget.id) {
      return "To " + _formatName(door.spaceLeadingTo);
    }
    return "From " + _formatName(door.spaceComingFrom);
  }
}
