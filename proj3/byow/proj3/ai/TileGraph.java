package byow.proj3.ai;

import byow.TileEngine.TETile;
import byow.proj3.Tile;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TileGraph implements AStarGraph<Tile>, Serializable {

    private Map<String, Tile> tiles = new HashMap<>();
    private Map<String, List<WeightedEdge<Tile>>> neighbors = new HashMap<>();

    public TileGraph(TETile[][] world) {

        for (int i = 0; i < world.length; i += 1) {
            for (int j = 0; j < world[i].length; j += 1) {
                if (world[i][j].description().equals("floor")) {

                    Tile t = new Tile(i, j);

                    tiles.put(t.getId(), t);

                    List<WeightedEdge<Tile>> n = new ArrayList<>();

                    if (!world[i - 1][j].description().equals("wall")) {
                        WeightedEdge<Tile> w = new WeightedEdge<>(t, new Tile(i - 1, j), 1);
                        n.add(w);
                    }

                    if (!world[i + 1][j].description().equals("wall")) {
                        WeightedEdge<Tile> w = new WeightedEdge<>(t, new Tile(i + 1, j), 1);
                        n.add(w);
                    }

                    if (!world[i][j + 1].description().equals("wall")) {
                        WeightedEdge<Tile> w = new WeightedEdge<>(t, new Tile(i, j + 1), 1);
                        n.add(w);
                    }

                    if (!world[i][j - 1].description().equals("wall")) {
                        WeightedEdge<Tile> w = new WeightedEdge<>(t, new Tile(i, j - 1), 1);
                        n.add(w);
                    }

                    neighbors.put(t.getId(), n);
                }
            }
        }
    }

    public List<WeightedEdge<Tile>> neighbors(Tile t) {
        return neighbors.get(t.getId());
    }

    public double estimatedDistanceToGoal(Tile s, Tile goal) {
        return Math.pow(Math.pow((s.getX() - goal.getX()), 2)
                + Math.pow((s.getY() - goal.getY()), 2), .5);
    }
}
