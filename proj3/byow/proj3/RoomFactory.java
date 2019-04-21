package byow.proj3;

import byow.TileEngine.TETile;

import java.util.*;

public class RoomFactory {

    private Random rng;
    private int worldW = Constants.WIDTH - 1;
    private int worldH = Constants.HEIGHT - 1;
    private int roomArea = 0;
    private int hallwayArea = 0;
    private List<Room> rooms = new ArrayList<>();
    private List<Hallway> hallways = new ArrayList<>();
    private UnionFind connectedGrid;

    public RoomFactory(TETile[][] world, Random rng) {
        this.rng = rng;
        while (percentRoom() < 0.5) {
            this.genRoom().putTiles(world);
        }
        connectedGrid = new UnionFind(rooms.size());
        while (percentHallway() < 0.30) {
            this.genHallway();
        }

        for (Hallway h : hallways) {
            h.putTiles(world);
        }

        System.out.println(connectedGrid.percentUnioned());

        /**
        int biggestMaze = connectedGrid.largestUnion();
        int smallestMaze = connectedGrid.smallestUnion();
        Room centerRoom = rooms.get(biggestMaze);
        Room lonelyRoom = rooms.get(smallestMaze);
         */

        //randomly generate a hallway
        //see if room hallway connects to connects to center Room
        //if it doesn't delete it and try again


    }



    public List<Hallway> getHallways() {
        return hallways;
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public double percentRoom() {
        return (double) roomArea / (double) (Constants.WIDTH * Constants.HEIGHT);
    }

    public double percentHallway() {
        return (double) hallwayArea / (double) (Constants.WIDTH * Constants.HEIGHT);
    }

    public void genHallway() {
        int dim = rng.nextInt(Constants.maxHallwayDim
                - Constants.minHallwayDim) + Constants.minHallwayDim;

        int x = Math.floorMod(rng.nextInt(), worldW);
        int y = Math.floorMod(rng.nextInt(), worldH);

        int o = rng.nextInt(2);
        Hallway.hallStates orientation;
        if (o == 0) {
            orientation = Hallway.hallStates.LR;
        } else {
            orientation = Hallway.hallStates.UD;
        }

        Hallway h = new Hallway(new Tile(x, y), orientation, dim);
        if (!h.insideWorld() || overlapsH(h) || !overlapsTwoRooms(h)) {
            return;
        }
        hallwayArea += h.size();
        hallways.add(h);
    }

    public Room genRoom() {

        int w = rng.nextInt(Constants.maxRoomDim
                - Constants.minRoomWidth) + Constants.minRoomWidth;

        int h = rng.nextInt(Constants.maxRoomDim
                - Constants.minRoomWidth) + Constants.minRoomWidth;

        int x = Math.floorMod(rng.nextInt(), worldW);
        int y = Math.floorMod(rng.nextInt(), worldH);

        Room temp = new Room(new Tile(x, y), w, h);

        if (temp.insideWorld() && !overlapsOne(temp)) {
            rooms.add(temp);
            roomArea += temp.size();
            return temp;
        } else {
            return genRoom();
        }
    }

    private boolean overlapsOne(Room r) {
        for (Room n : rooms) {
            if (n.overlap(r)) {
                return true;
            }
        }
        return false;
    }

    private boolean overlapsTwoRooms(Hallway h) {
        int counter = 0;
        List<Room> connectedRooms = new ArrayList<>();
        for (Room n : rooms) {
            if (h.overlap(n)) {
                counter += 1;
                connectedRooms.add(n);

            }
        }
        if (counter >= 2) {
            Room r1 = connectedRooms.get(0);
            for (int i = 1; i < connectedRooms.size(); i++) {
                connectedGrid.union(rooms.indexOf(r1),
                        rooms.indexOf(connectedRooms.get(i)));
            }
            return true;
        }
        return false;
    }

    private boolean overlapsH(Hallway h) {
        for (Hallway n : hallways) {
            if (h.overlap(n)) {
                return true;
            }
        }
        return false;
    }
}
