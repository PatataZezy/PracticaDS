import 'package:flutter/material.dart';
import 'package:tutorial_acs/tree.dart';
import 'package:tutorial_acs/requests.dart';

class ScreenSpace extends StatefulWidget {
  final String id;

  const ScreenSpace({super.key, required this.id});

  @override
  State<ScreenSpace> createState() => _ScreenSpaceState();
}

class _ScreenSpaceState extends State<ScreenSpace> {
  late Future<Tree> futureTree;

  @override
  void initState() {
    super.initState();
    futureTree = getTree(widget.id);
  }

  void _refresh() {
    setState(() {
      futureTree = getTree(widget.id);
    });
  }

  @override
  Widget build(BuildContext context) {
    final screenSize = MediaQuery.of(context).size;

    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.primary,
        foregroundColor: Theme.of(context).colorScheme.onPrimary,
        title: Text(widget.id),
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
                        door.id,
                        style: const TextStyle(
                          fontSize: 18,
                          fontWeight: FontWeight.bold,
                        ),
                      ),
                      const SizedBox(height: 4),
                      Text(
                        'Current state: ${door.state}',
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

    // Lock/Unlock button based on state
    if (door.state == 'locked') {
      buttons.add(
        ElevatedButton(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.green,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
          ),
          onPressed: () {
            unlockDoor(door);
            _refresh();
          },
          child: const Text('Unlock'),
        ),
      );
    } else {
      buttons.add(
        ElevatedButton(
          style: ElevatedButton.styleFrom(
            backgroundColor: Colors.red,
            foregroundColor: Colors.white,
            padding: const EdgeInsets.symmetric(horizontal: 20, vertical: 12),
            shape: RoundedRectangleBorder(
              borderRadius: BorderRadius.circular(20),
            ),
          ),
          onPressed: () {
            lockDoor(door);
            _refresh();
          },
          child: const Text('Lock'),
        ),
      );
    }

    return buttons;
  }

  Color _getStateColor(String state) {
    switch (state) {
      case 'locked':
        return Colors.red;
      case 'unlocked':
        return Colors.green;
      case 'unlocked_shortly':
        return Colors.orange;
      case 'propped':
        return Colors.deepOrange;
      default:
        return Colors.grey;
    }
  }
}
