import 'dart:convert' as convert;
import 'package:http/http.dart' as http;
import 'package:intl/intl.dart';
import 'tree.dart';

const String BASE_URL = "http://localhost:8080";
final DateFormat DATEFORMATTER = DateFormat('yyyy-MM-ddTHH:mm');

Future<http.Response> sendRequest(Uri uri) async {
  return http.get(uri).then((http.Response response) {
    if (response.statusCode == 200) {
      print("statusCode=${response.statusCode}");
      print(response.body);
    } else {
      print("statusCode=${response.statusCode}");
      throw Exception('failed to get answer to request $uri');
    }
    return response;
  });
}

Future<Tree> getTree(String areaId) async {
  Uri uri = Uri.parse("$BASE_URL/get_children?$areaId");
  return sendRequest(uri).then((http.Response response) {
    Map<String, dynamic> decoded = convert.jsonDecode(response.body);
    return Tree.fromJson(decoded);
  });
}

Future<Tree> getDoorsRelated(String spaceId) async {
  Uri uri = Uri.parse("$BASE_URL/related_doors?$spaceId");
  return sendRequest(uri).then((http.Response response) {
    Map<String, dynamic> decoded = convert.jsonDecode(response.body);
    return Tree.fromJson(decoded);
  });
}


void openDoor(Door door) {
  performAction(door, "open");
}

void closeDoor(Door door) {
  performAction(door, "close");
}

void lockDoor(Door door) {
  performAction(door, 'lock');
}

void unlockDoor(Door door) {
  performAction(door, 'unlock');
}

void unlockDoorShortly(Door door) {
  performAction(door, 'unlock_shortly');
}

void performAction(Door door, String action) {
  assert((action == 'lock') || (action == 'unlock') || (action == "open") || (action == "close") || (action == "unlock_shortly"));
  String strNow = DATEFORMATTER.format(DateTime.now());
  print(DateTime.now());
  print(strNow);
  Uri uri = Uri.parse(
      "$BASE_URL/reader?credential=11343&action=$action&datetime=$strNow&doorId=${door.id}");
  print('door ${door.id} is ${door.state}');
  print('$action ${door.id}, uri $uri');
  sendRequest(uri);
}
