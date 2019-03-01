import java.util.ArrayList;
import java.util.List;

public class UnionFind {

    int[] parent;
    int[] size;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        parent = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i += 1) {
            parent[i] = -1;
            size[i] = 1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex >= parent.length) {
            throw new IllegalArgumentException();
        }
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        validate(v1);
        return size[v1];
    }

    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        validate(v1);
        if (parent[v1] == -1) {
            return -size[v1];
        }
        return parent[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {

        validate(v1);
        validate(v2);

        List<Integer> parentsv1 = new ArrayList<>();
        List<Integer> parentsv2 = new ArrayList<>();

        while (parent[v1] != -1) {
            parentsv1.add(v1);
            v1 = parent[v1];
        }

        while (parent[v2] != -1) {
            parentsv2.add(v2);
            v2 = parent[v2];
        }

        /* Checks if parentsv1 has item contained in parentsv2*/
        for (int i : parentsv1) {
            for (int j : parentsv2) {
                if (i == j) {
                    return true;
                }
            }
        }

        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */
    public void union(int v1, int v2) {
        if (sizeOf(v1) <= sizeOf(v2)) {
            parent[find(v2)] = find(v1);
        } else {
            parent[find(v1)] = find(v2);
        }
        size[v1] += size[v2];
        size[v2] += size[v1];
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        while (parent[vertex] != -1) {
            vertex = parent[vertex];
        }
        return vertex;
    }
}
