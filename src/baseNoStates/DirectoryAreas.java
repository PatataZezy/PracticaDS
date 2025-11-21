// In this code we define all the available areas in tree format and define them as partitions in the building

package baseNoStates;

public final class DirectoryAreas {
    private static Area rootArea;
    private static Door[] allDoors;

    public static void makeAreas() {
        // Creating partitions/spaces
        Partition building = new Partition("building", null);

        Partition basement = new Partition("basement", building);
        Space parking = new Space("parking", basement);

        Partition groundFloor = new Partition("ground_floor", building);
        Space hall = new Space("hall", groundFloor);
        Space room1 = new Space("room1", groundFloor);
        Space room2 = new Space("room2", groundFloor);

        Partition floor1 = new Partition("floor1", building);
        Space room3 = new Space("room3", floor1);
        Space corridor = new Space("corridor", floor1);
        Space IT = new Space("IT", floor1);

        Space stairs = new Space("stairs", building);
        Space exterior = new Space("exterior", building);

        // Create doors
        Door D1 = new Door("D1", parking);
        Door D2 = new Door("D2", parking);
        Door D3 = new Door("D3", hall);
        Door D4 = new Door("D4", hall);
        Door D5 = new Door("D5", room1);
        Door D6 = new Door("D6", room2);
        Door D7 = new Door("D7", corridor);
        Door D8 = new Door("D8", room3);
        Door D9 = new Door("D9", IT);

        rootArea = building;
        allDoors = new Door[]{D1, D2, D3, D4, D5, D6, D7, D8, D9};
    }

    public static Area findAreaById(String id) {
        return rootArea.findAreaById(id);
    }

    public static Door findDoorById(String id) {
        for (int i = 0; i < allDoors.length; i++) {
            if (allDoors[i].getId().equals(id)) {
                return allDoors[i];
            }
        }

        return null;
    }

    public static Door[] getAllDoors() {
        return allDoors;
    }
}
