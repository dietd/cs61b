package byow.proj3;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.*;

public class RoomFactory {

    private Random rng;
    private int worldW = Constants.WIDTH - 1;
    private int worldH = Constants.HEIGHT - 1;
    private int roomArea = 0;
    private int hallwayArea = 0;
    private List<Room> rooms = new ArrayList<>();
    private List<Hallway> hallways = new ArrayList<>();
    private UnionFind connections;

    public RoomFactory(TETile[][] world, Random rng) {

        this.rng = rng;
        int numRooms = rng.nextInt(40) + 1;
        int numHalls = rng.nextInt(40);
        connections = new UnionFind(numRooms);

        while (rooms.size() < numRooms) {
            this.genRoom();
        }

        while (hallways.size() < numHalls) {
            this.genHallway();
        }

        connections.printArray();

        for (Room r : rooms) {
            r.putTiles(world);
        }

        for (Hallway h : hallways) {
            h.putTiles(world);
        }

    }

    private void clean() {
        
    }

    public Room getRandomRoom() {
        if (rooms.size() == 1) {
            return rooms.get(0);
        }
        return rooms.get(rng.nextInt(rooms.size() - 1));
    }

    public Room genRoom() {

        int w = rng.nextInt(Constants.maxRoomDim
                - Constants.minRoomDim) + Constants.minRoomDim;

        int h = rng.nextInt(Constants.maxRoomDim
                - Constants.minRoomDim) + Constants.minRoomDim;

        int x = Math.floorMod(rng.nextInt(), worldW);
        int y = Math.floorMod(rng.nextInt(), worldH);

        Room temp = new Room(new Tile(x, y), w, h, rooms.size());

        if (temp.insideWorld() && !overlapsRoom(temp)) {
            rooms.add(temp);
            roomArea += temp.size();
            return temp;
        }

        return null;
    }

    private boolean overlapsRoom(Room r) {

        for (Room n : rooms) {
            if (n.overlap(r)) {
                return true;
            }
        }

        return false;
    }

    public void genHallway() {

        int dim = rng.nextInt(Constants.maxHallwayDim
                - Constants.minHallwayDim) + Constants.minHallwayDim;

        int x = Math.floorMod(rng.nextInt(), worldW);
        int y = Math.floorMod(rng.nextInt(), worldH);

        int o = Math.floorMod(rng.nextInt(), 2);
        Hallway.hallStates orientation;

        if (o == 0) {
            orientation = Hallway.hallStates.LR;
        } else {
            orientation = Hallway.hallStates.UD;
        }

        Hallway h = new Hallway(new Tile(x, y), orientation, dim);

        List<Room> rlist = connectedRooms(h);
        List<Hallway> hlist = connectedHalls(h);

        if (h.insideWorld() && ((hlist.size() + rlist.size()) > 1) &&
            !overlapsRoom(h) && !overlapsHall(h)) {
            hallways.add(h);
            connectAll(rlist);
        }
    }

    private void connectAll(List<Room> rlist) {
        if (rlist.isEmpty()) {
           return;
        }
        int inital = rlist.get(0).index();
        for (Room r : rlist) {
            connections.union(inital, r.index());
        }
    }

    private List<Hallway> connectedHalls(Hallway h1) {
        List<Hallway> t = new ArrayList<>();
        for (Hallway h2 : hallways) {
            if (h1.connected(h2)) {
                t.add(h2);
            }
        }
        return t;
    }

    private List<Room> connectedRooms(Hallway h) {
        List<Room> t = new ArrayList<>();
        for (Room n : rooms) {
            if (h.connected(n)) {
                t.add(n);
            }
        }
        return t;
    }

    private boolean overlapsRoom(Hallway h) {
        for (Room r : rooms) {
            if ((h.overlap(r) && !h.connected(r)) || h.insideRoom(r)) {
                return true;
            }
        }
        return false;
    }

    private boolean overlapsHall(Hallway h1) {
        for (Hallway h2 : hallways) {
            if (h1.overlap(h2) && !h1.connected(h2)) {
                return true;
            }
        }
        return false;
    }

    /**
     public boolean connectRooms(Room r1, Room r2, int num) {
     Tile tile1 = r1.getRandomInside(rng);
     Tile tile2 = r2.getRandomInside(rng);

     System.out.println(tile1 + " " + tile2);

     Tile midTile;
     if (num == 0) {
     midTile = new Tile(tile2.getX(), tile1.getY());
     } else {
     midTile = new Tile(tile1.getX(), tile2.getY());
     }

     Hallway h1 = Hallway.getLine(tile1, midTile);
     Hallway h2 = Hallway.getLine(tile2, midTile);

     if (validateHallway(h1) && validateHallway(h2)) {
     hallways.add(h1);
     hallways.add(h2);
     return true;
     }

     return false;
     }

     private boolean validateHallway(Hallway h) {
     return h.insideWorld();
     }

     */
}
