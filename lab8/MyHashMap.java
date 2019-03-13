import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private int n;
    private int m;
    private double lf;
    private LinkedList<Node>[] table;
    private Set<K> keys;


    private class Node {
        private K key;
        private V value;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public MyHashMap() {
        n = 0;
        m = 16;
        lf = 0.75;
        table = (LinkedList<Node>[]) new LinkedList[m];
        keys = new HashSet<>();
        for (int i = 0; i < m; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public MyHashMap(int initialSize) {
        n = 0;
        m = initialSize;
        lf = 0.75;
        table = (LinkedList<Node>[]) new LinkedList[m];
        keys = new HashSet<>();

        for (int i = 0; i < m; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public MyHashMap(int initialSize, double loadFactor) {
        n = 0;
        m = initialSize;
        lf = loadFactor;
        table = (LinkedList<Node>[]) new LinkedList[m];
        keys = new HashSet<>();

        for (int i = 0; i < m; i++) {
            table[i] = new LinkedList<>();
        }
    }

    private void resize() {
        if (((double) n / (double) m) > lf) {

            m *= 2;
            LinkedList<Node>[] newtable = (LinkedList<Node>[]) new LinkedList[m];
            for (int i = 0; i < m; i++) {
                newtable[i] = new LinkedList<>();
            }

            for (int j = 0; j < table.length; j += 1) {
                for (Node n : table[j]) {
                    newtable[hash(n.key)].add(n);
                }
            }

            table = newtable;

        }
    }

    public void clear() {

        table = (LinkedList<Node>[]) new LinkedList[16];
        keys = new HashSet<>();
        n = 0;
        m = 16;

        for (int i = 0; i < table.length; i++) {
            table[i] = new LinkedList<>();
        }
    }

    public boolean containsKey(K key) {
        return keys.contains(key);
    }

    public V get(K key) {

        for (Node n : table[hash(key)]) {
            if (n.key.equals(key)) {
                return n.value;
            }
        }

        return null;
    }

    public int size() {
        return n;
    }

    public void put(K key, V value) {
        resize();
        if (containsKey(key)) {
            for (Node n : table[hash(key)]) {
                if (n.key.equals(key)) {
                   n.value = value;
                }
            }
            return;
        }
        keys.add(key);
        table[hash(key)].add(new Node(key, value));
        n += 1;
    }

    public Set<K> keySet() {
        return keys;
    }

    public V remove(K key) {
        throw new UnsupportedOperationException();
        /** Node toRemove = null;

        for (Node n : table[hash(key)]) {
            if (n.key == key) {
                toRemove = n;
            }
        }

        if (toRemove == null) {
            return null;
        }

        V value = toRemove.value;
        table[hash(key)].remove(toRemove);
        return value; */
    }

    public V remove(K key, V value) {
        throw new UnsupportedOperationException();

        /** Node toRemove = null;

        for (Node n : table[hash(key)]) {
            if (n.key == key) {
                toRemove = n;
            }
        }

        if (toRemove == null) {
            return null;
        } else if (toRemove.value == value) {
            V v = toRemove.value;
            keys.remove(key);
            table[hash(key)].remove(toRemove);
            return v;
        } else {
            return null;
        } */
    }

    public Iterator<K> iterator() {
        return keys.iterator();
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }
}
