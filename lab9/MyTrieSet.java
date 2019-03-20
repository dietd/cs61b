import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyTrieSet implements TrieSet61B {

    private Node root;

    private class Node {
        private String str;
        private boolean isKey;
        private Map<Character, Node> map;
        public Node(String str, boolean isKey) {
            this.str = str;
            this.isKey = isKey;
            this.map = new HashMap<>();
        }
    }

    public MyTrieSet() {
        root = new Node(null, false);
    }

    public void clear() {
        root = new Node(null, false);
    }

    public boolean contains(String key) {

        Node curr = root;
        for (int i = 0; i < key.length(); i += 1) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                return false;
            }
            curr = curr.map.get(c);
        }

        if (curr.isKey) {
            return true;
        }
        return false;
    }

    public void add(String key) {
        if (key == null || contains(key)) {
            return;
        }
        Node curr = root;
        for (int i = 0; i < key.length(); i += 1) {
            char c = key.charAt(i);
            if (!curr.map.containsKey(c)) {
                curr.map.put(c, new Node(Character.toString(c), false));
            }
            curr = curr.map.get(c);
        }
        curr.isKey = true;
    }

    public List<String> keysWithPrefix(String prefix) {
        Node curr = root;
        List<String> keys = new ArrayList<>();
        for (int i = 0; i < prefix.length(); i += 1) {
            char c = prefix.charAt(i);
            if (!curr.map.containsKey(c)) {
                return keys;
            }
            curr = curr.map.get(c);
        }
        for (String s : getStrings(curr)) {
            keys.add(prefix.substring(0, prefix.length() - 1) + s);
        }
        return keys;
    }

    private List<String> getStrings(Node curr) {
        List<String> paths = new ArrayList<>();
        if (curr.isKey) {
            paths.add(curr.str);
        }
        for (Node n : curr.map.values()) {
            for (String s : getStrings(n)) {
                if (curr.str == null) {
                    paths.add(s);
                } else {
                    paths.add(curr.str + s);
                }
            }
        }
        return paths;
    }

    public String longestPrefixOf(String key) {
        throw new UnsupportedOperationException();
    }

}
