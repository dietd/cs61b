package byow.proj3;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class RoomFactory {

    private int maxRoomW;
    private int maxRoomH;
    private Random seed;
    private int windowW;
    private int windowH;
    private int size = 0;
    private static final int minW = 4;
    private static final int minH = 4;
    private Set<Room> rooms = new HashSet<>();

    public RoomFactory(Random s, int width, int height) {
        maxRoomW = Math.floorDiv(width, 10) + 2;
        maxRoomH = maxRoomW;
        windowW = width - 1;
        windowH = height - 1;
        seed = s;
    }

    public double percentCovered() {
        return (double) size / (double) ((windowW + 1) * (windowH + 1));
    }

    public Room genRoom() {

        int w = seed.nextInt(maxRoomW);

        if (w < minW) {
            w = minW;
        }

        int h = seed.nextInt(maxRoomH);

        if (h < minH) {
            h = minH;
        }

        int x = Math.floorMod(seed.nextInt(), windowW);
        int y = Math.floorMod(seed.nextInt(), windowH);

        if (x + w - 1 > windowW) {
            return genRoom();
        } else if (y + h - 1 > windowH) {
            return genRoom();
        }

        System.out.println(w + " " + h);

        System.out.println(x + " " + y);

        Room temp = new Room(new Tile(x, y), w, h);
        if (temp.insideWorld() && !overlapsOne(temp)) {
            rooms.add(temp);
            size += temp.size();
            return temp;
        } else {
            return genRoom();
        }
    }

    public boolean overlapsOne(Room r) {
        for (Room n : rooms) {
            if (n.overlap(r)) {
                return true;
            }
        }
        return false;
    }
}
