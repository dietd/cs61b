package byow.proj3;

public class UnionFind {
    private int[] unionArray;

    /* Creates a UnionFind data structure holding n vertices. Initially, all
       vertices are in disjoint sets. */
    public UnionFind(int n) {
        unionArray = new int[n];
        for (int i = n - 1; i >= 0; i -= 1) {
            unionArray[i] = -1;
        }
    }

    /* Throws an exception if v1 is not a valid index. */
    private void validate(int vertex) {
        if (vertex < 0 || vertex > unionArray.length) {
            throw new IllegalArgumentException();
        }
        return;
    }

    /* Returns the size of the set v1 belongs to. */
    public int sizeOf(int v1) {
        return -1 * unionArray[find(v1)];
    }

    public int largestUnion() {
        int maxIndex = 0;
        int maxVal = 0;
        for (int i = 0; i < unionArray.length; i++) {
            if (Math.abs(unionArray[i]) > maxVal) {
                maxVal = unionArray[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    public double percentUnioned() {
        return (double) (sizeOf(largestUnion())) / (double) unionArray.length;
    }


    /* Returns the parent of v1. If v1 is the root of a tree, returns the
       negative size of the tree for which v1 is the root. */
    public int parent(int v1) {
        if (unionArray[v1] < 0) {
            return v1;
        }
        return unionArray[v1];
    }

    /* Returns true if nodes v1 and v2 are connected. */
    public boolean connected(int v1, int v2) {
        if (find(v1) == find(v2) && find(v1) != -1) {
            return true;
        }
        return false;
    }

    /* Connects two elements v1 and v2 together. v1 and v2 can be any valid 
       elements, and a union-by-size heuristic is used. If the sizes of the sets
       are equal, tie break by connecting v1's root to v2's root. Unioning a 
       vertex with itself or vertices that are already connected should not 
       change the sets but may alter the internal structure of the data. */

    public void union(int v1, int v2) {
        if (connected(v1, v2)) {
            return;
        }
        if (sizeOf(v1) > sizeOf(v2)) {
            int newConnection = find(v1);
            unionArray[newConnection] -= sizeOf(v2);
            unionArray[v2] = newConnection;

            //v2 to v1
        } else {
            //v1 to v2, or equal size
            int newConnection = find(v2);
            unionArray[newConnection] -= sizeOf(v1);
            unionArray[v1] = newConnection;

        }
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. */
    public int find(int vertex) {
        validate(vertex);
        if (parent(vertex) == vertex) {
            return vertex;
        }
        int newParent = find(parent(vertex));
        unionArray[vertex] = newParent;
        return newParent;
    }

}
