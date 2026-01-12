import 'dart:collection';

String language = "English";

String translate(String message) {
  final HashMap<String, String> toCatalan = HashMap.of({
    "ACS System" : "Sistema de control d'accés",

    "Building" : "Edifici",
    "Basement" : "Subterrani",
    "Ground floor " : "Planta baixa",
    "Floor 1" : "Primer pis",
    "Stairs" : "Escales",
    "Exterior" : "Exterior",
    "Parking" : "Aparcament",
    "Hall" : "Sala principal",
    "Room 1" : "Habitació 1",
    "Room 2" : "Habitació 2",
    "Room 3" : "Habitació 3",
    "Corridor" : "Passadís",
    "IT" : "Sala TIC",

    "From " : "Des de ",
    "To " : "Cap a ",
    "Current state: " : "Estat actual: ",
    "Opened" : "Oberta",
    "Closed" : "Tancada",
    "Unlocked" : "Desbloquejada",
    "Locked" : "Bloquejada",
    "Unlocked shortly" : "Desbloquejada momentàniament",
    "Propped" : "Recolzada",

    "Open" : "Obrir",
    "Close" : "Tancar",
    "Lock" : "Bloquejar",
    "Unlock" : "Desbloquejar",
    "Unlock shortly" : "Desbloquejar momentàniament",

    "Lock all doors here" : "Bloquejar totes les portes d'aquí",
    "Unlock all doors here" : "Desbloquejar totes les portes d'aquí"
  });

  final HashMap<String, String> toSpanish = HashMap.of({
    "ACS System" : "Sistema de control de accesso",

    "Building" : "Edificio",
    "Basement" : "Subterráneo",
    "Ground floor " : "Planta baja",
    "Floor 1" : "Primer piso",
    "Stairs" : "Escaleras",
    "Exterior" : "Exterior",
    "Parking" : "Aparcamiento",
    "Hall" : "Sala principal",
    "Room 1" : "Habitacion 1",
    "Room 2" : "Habitacion 2",
    "Room 3" : "Habitacion 3",
    "Corridor" : "Pasadizo",
    "IT" : "Sala TIC",

    "From " : "Desde ",
    "To " : "Hacia ",
    "Current state: " : "Estado actual: ",
    "Opened" : "Abierta",
    "Closed" : "Cerrada",
    "Unlocked" : "Desbloqueada",
    "Locked" : "Bloqueada",
    "Unlocked shortly" : "Desbloqueada momentáneamente",
    "Propped" : "Apuntalada",

    "Open" : "Abrir",
    "Close" : "Cerrar",
    "Lock" : "Bloquear",
    "Unlock" : "Desbloquear",
    "Unlock shortly" : "Desbloquear momentáneamente",

    "Lock all doors here" : "Bloquear todas les puertas de aquí",
    "Unlock all doors here" : "Desbloquear todas les puertas de aquí"
  });

  switch (language) {
    case ("Català"):
      if (!toCatalan.containsKey(message)) {
        print(message);
      }
      return toCatalan[message] ?? message;
    case ("Castellano"):
      return toSpanish[message] ?? message;
    default:
      return message;
  }
}