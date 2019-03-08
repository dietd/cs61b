import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V>{
    private Node root;
    private Set<K> keyset = new HashSet<>();
    private int size = 0;

    private class Node{
        private K key;
        private V value;
        private Node left, right;
        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @Override
    public void clear() {
        root = null;
        keyset = new HashSet<>();
        size = 0;
    }

    @Override
    public boolean containsKey(K key){
        return keyset.contains(key);
    }

    @Override
    public V get(K key) {
        if (!containsKey(key)) {
            return null;
        }
        return get(root, key);
    }

    private V get(Node node, K key) {
        int comp = key.compareTo(node.key);
        if (comp < 0) {
            return get(node.left, key);
        } else if (comp > 0) {
            return get(node.right, key);
        } else {
            return node.value;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void put(K key, V value) {
        if (size == 0) {
            root = new Node(key, value);
            keyset.add(key);
            size += 1;
        } else {
            Node newNode = new Node(key, value);
            put(root, newNode);
            keyset.add(key);
            size += 1;
        }
    }

    private void put(Node parent, Node node) {
        int comp = node.key.compareTo(parent.key);
        if (comp < 0) {
            if (parent.left == null) {
                parent.left = node;
            } else {
                put(parent.left, node);
            }
        } else if (comp > 0) {
            if (parent.right == null) {
                parent.right = node;
            } else {
                put(parent.right, node);
            }
        } else {
            parent.value = node.value;
        }
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException();
    }

    public void printInOrder() {
        printTree(root);
    }

    private void printTree(Node node) {
        if (node == null) {
            return;
        }
        printTree(node.left);
        System.out.println(node.key.toString() + " ");
        printTree(node.right);
    }
}
