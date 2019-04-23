package byow.proj3;

import byow.TileEngine.TETile;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class RoomFactory {

    private Random rng;
    private int worldW = Constants.WIDTH - 1;
    private int worldH = Constants.HEIGHT - 1;
    private int numRooms;
    private int numHallways;
    private List<Room> rooms = new ArrayList<>();
    private List<Hallway> hallways = new ArrayList<>();
    private UnionFind connections;

    public RoomFactory(TETile[][] world, Random rng) {

        this.rng = rng;

        this.numRooms = 40;
        this.numHallways = rng.nextInt(50 - numRooms) + numRooms;

        connections = new UnionFind(numRooms + numHallways);

        while (rooms.size() < numRooms) {
            this.genRoom();
        }

        while (hallways.size() < numHallways) {
            this.genHallway();
        }

        clean();

        for (Room r : rooms) {
            r.putTiles(world);
        }

        for (Hallway h : hallways) {
            h.putTiles(world);
        }
    }

    private void clean() {
        int index = connections.maxIndex();

        List<Room> roomsRemove = new ArrayList<>();
        List<Hallway> hallwaysRemove = new ArrayList<>();


        for (Room r : rooms) {
            if (!connections.connected(r.index(), index)) {
                roomsRemove.add(r);
            }
        }

        for (Hallway h : hallways) {
            if (!connections.connected(h.index() + numRooms, index)) {
                hallwaysRemove.add(h);
            }
        }

        for (Room r : roomsRemove) {
            rooms.remove(r);
        }

        for (Hallway h : hallwaysRemove) {
            hallways.remove(h);
        }

    }

    public Room genRoom() {

        int w = rng.nextInt(Constants.MAX_ROOM_DIM
                - Constants.MIN_ROOM_DIM) + Constants.MIN_ROOM_DIM;

        int h = rng.nextInt(Constants.MAX_ROOM_DIM
                - Constants.MIN_ROOM_DIM) + Constants.MIN_ROOM_DIM;

        int x = Math.floorMod(rng.nextInt(), worldW);
        int y = Math.floorMod(rng.nextInt(), worldH);

        Room temp = new Room(new Tile(x, y), w, h, rooms.size());

        if (temp.insideWorld() && !overlapsRoom(temp)) {
            rooms.add(temp);
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

        int dim = rng.nextInt(Constants.MAX_HALLWAY_DIM
                - Constants.MIN_HALLWAY_DIM) + Constants.MIN_HALLWAY_DIM;

        int x = Math.floorMod(rng.nextInt(), worldW);
        int y = Math.floorMod(rng.nextInt(), worldH);

        int o = Math.floorMod(rng.nextInt(), 2);
        Hallway.HallStates orientation;

        if (o == 0) {
            orientation = Hallway.HallStates.LR;
        } else {
            orientation = Hallway.HallStates.UD;
        }

        Hallway h = new Hallway(new Tile(x, y), orientation, dim, hallways.size());

        List<Room> rlist = connectedRooms(h);
        List<Hallway> hlist = connectedHalls(h);

        if (h.insideWorld() && ((hlist.size() + rlist.size()) >= 1)
                && !overlapsRoom(h) && !overlapsHall(h)) {

            hallways.add(h);

            for (Room room : rlist) {
                connections.union(h.index() + numRooms, room.index());
            }

            for (Hallway hall : hlist) {
                connections.union(h.index() + numRooms, hall.index() + numRooms);
            }
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
