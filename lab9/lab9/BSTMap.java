package lab9;

import java.util.*;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;


        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

//    private class nodeAndValOfRemove {
//        private Node node;
//        private V value;
//        private nodeAndValOfRemove(Node node) {
//            this.node = node;
//        }
//    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    private List<K> listOfKey;

    private V valueOfRemovedItem;

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
        listOfKey = new ArrayList<K>();
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            return new Node(key, value);
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        if (value == null) {
            remove(key);
        }
        root = putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        keyList(root, listOfKey);
        return new HashSet<K>(listOfKey);
    }

    private void keyList(Node p, List<K> list) {
        if (p == null) {
            return;
        }
        keyList(p.left, list);
        list.add(p.key);
        keyList(p.right, list);
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        this.valueOfRemovedItem = null;
        root = removeHelper(key, null, root);
        return this.valueOfRemovedItem;
    }


    private Node deleteMax(Node p) {
        if (p.right == null) {
            return p.left;
        }
        p.right = deleteMax(p.right);
        return p;
    }

    private Node max(Node p) {
        while (p.right != null) {
            p = p.right;
        }
        return p;
    }

    private Node deleteMin(Node p) {
        if (p.left == null) {
            return p.right;
        }
        p.left = deleteMin(p.left);
        return p;
    }

    private Node min(Node p) {
        while (p.left != null) {
            p = p.left;
        }
        return p;
    }




    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("key is null");
        }
        this.valueOfRemovedItem = null;
        root = removeHelper(key, value, root);
        return this.valueOfRemovedItem;
    }

    private Node removeHelper(K key, V val, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = removeHelper(key, val, p.left);
        } else if (cmp > 0) {
            p.right = removeHelper(key, val, p.right);
        } else {
            if (val == null || p.value.equals(val)) {
                this.valueOfRemovedItem = p.value;
                if (p.left == null) {
                    size -= 1;
                    return p.right;
                } else if (p.right == null) {
                    size -= 1;
                    return p.left;
                } else {
                    Node min = min(p.right);
                    p.right = deleteMin(p.right);
                    size -= 1;
                    p.key = min.key;
                    p.value = min.value;
                    return p;
                }
            }
        }
        return p;
    }

    @Override
    public Iterator<K> iterator() {
        return new BSTiterator();
    }

    private class BSTiterator implements Iterator<K> {
        private int wizPos;

        public BSTiterator() {
            wizPos = 0;
        }

        public boolean hasNext() {
            return wizPos < size;
        }

        public K next() {
            K returnItem = listOfKey.get(wizPos);
            wizPos += 1;
            return returnItem;
        }
    }


    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        System.out.println(bstmap.remove("hello"));
        System.out.println(bstmap.remove("cat"));
        System.out.println(bstmap.keySet());


    }
}

