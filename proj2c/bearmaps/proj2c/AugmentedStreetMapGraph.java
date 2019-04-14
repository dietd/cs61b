package bearmaps.proj2c;

import bearmaps.hw4.streetmap.Node;
import bearmaps.hw4.streetmap.StreetMapGraph;
import bearmaps.proj2ab.KDTree;
import bearmaps.proj2ab.Point;

import java.util.*;

/**
 * An augmented graph that is more powerful that a standard StreetMapGraph.
 * Specifically, it supports the following additional operations:
 *
 *
 * @author Alan Yao, Josh Hug, ________
 */
public class AugmentedStreetMapGraph extends StreetMapGraph {

    private Map<Point, Node> nmap;
    private KDTree kdTree;
    private TrieSet names;
    private Map<String, ArrayList<Node>> qmap;

    public AugmentedStreetMapGraph(String dbPath) {
        super(dbPath);

        List<Node> nodes = this.getNodes();
        List<Point> points = new ArrayList<>();

        nmap = new HashMap<>();
        qmap = new HashMap<>();
        names = new TrieSet();

        for (Node n : nodes) {
            if (!this.neighbors(n.id()).isEmpty()) {
                Point p = new Point(n.lon(), n.lat());
                points.add(p);
                String cleanName = n.name();
                names.add(cleanName);

                if (qmap.containsKey(cleanName)) {
                    qmap.get(cleanName).add(n);
                } else {
                    ArrayList<Node> al = new ArrayList<>();
                    al.add(n);
                    qmap.put(cleanName, al);
                }

                nmap.put(p, n);
            }
        }

        kdTree = new KDTree(points);
    }


    /**
     * For Project Part II
     * Returns the vertex closest to the given longitude and latitude.
     * @param lon The target longitude.
     * @param lat The target latitude.
     * @return The id of the node in the graph closest to the target.
     */
    public long closest(double lon, double lat) {
        return nmap.get(kdTree.nearest(lon, lat)).id();
    }

    /**
     * For Project Part III (gold points)
     * In linear time, collect all the names of OSM locations that prefix-match the query string.
     * @param prefix Prefix string to be searched for. Could be any case, with our without
     *               punctuation.
     * @return A <code>List</code> of the full names of locations whose cleaned name matches the
     * cleaned <code>prefix</code>.
     */
    public List<String> getLocationsByPrefix(String prefix) {
        return names.keysWithPrefix(prefix);
    }

    /**
     * For Project Part III (gold points)
     * Collect all locations that match a cleaned <code>locationName</code>, and return
     * information about each node that matches.
     * @param locationName A full name of a location searched for.
     * @return A list of locations whose cleaned name matches the
     * cleaned <code>locationName</code>, and each location is a map of parameters for the Json
     * response as specified: <br>
     * "lat" -> Number, The latitude of the node. <br>
     * "lon" -> Number, The longitude of the node. <br>
     * "name" -> String, The actual name of the node. <br>
     * "id" -> Number, The id of the node. <br>
     */
    public List<Map<String, Object>> getLocations(String locationName) {
        List<Map<String, Object>> locations = new ArrayList<>();
        for (Node n : qmap.get(locationName)) {
            Map<String, Object> m = new HashMap<>();
            m.put("lon", n.lon());
            m.put("lat", n.lat());
            m.put("name", n.name());
            m.put("id", n.id());
            locations.add(m);
        }
        return locations;
    }

    /**
     * Useful for Part III. Do not modify.
     * Helper to process strings into their "cleaned" form, ignoring punctuation and capitalization.
     * @param s Input string.
     * @return Cleaned string.
     */
    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

}
