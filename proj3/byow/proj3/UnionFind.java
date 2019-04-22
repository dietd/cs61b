package byow.proj3;

public class UnionFind {
    private int[] unions;

    public UnionFind(int n) {
        unions = new int[n];
        for (int i = 0; i < unions.length; i += 1) {
            unions[i] = -1;
        }
    }

    public int find(int a) {
        if (unions[a] < 0) {
            return a;
        }
        return find(unions[a]);
    }

    public int sizeOf(int a) {
        return -1 * unions[find(a)];
    }

    public void union(int a, int b) {
        if (a == b) {
            return;
        }
        if (unions[a] < 0 && unions[b] < 0) {
            if (sizeOf(a) > sizeOf(b)) {
                unions[a] += unions[b];
                unions[b] = a;
            } else {
                unions[b] += unions[a];
                unions[a] = b;
            }
        } else {
            union(find(a), find(b));
        }
    }

    public boolean connected(int a, int b) {
        return find(a) == find(b);
    }

    public int maxIndex() {

        int index = 0;
        int max = 0;

        for (int i = 0; i < unions.length; i += 1) {
            int temp = sizeOf(i);
            if (temp > max) {
                index = i;
                max = temp;
            }
        }

        return index;
    }

    public void printArray() {
        System.out.print("Unions: ");
        for (int i : unions) {
            System.out.print(i + " ");
        }
    }
}
