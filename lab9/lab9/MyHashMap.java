package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    public MyHashMap(int capacity) {
        buckets = new ArrayMap[capacity];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }

        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int keyIndex = hash(key);
        return this.buckets[keyIndex].get(key);
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (value == null) {
            remove(key);
            return;
        }
        if (loadFactor() > MAX_LF) {
            resizing(this.buckets.length * 2);
        }
        int keyIndex = hash(key);
        if (!this.buckets[keyIndex].containsKey(key)) {
            this.size += 1;
        }
        this.buckets[keyIndex].put(key, value);
    }

    private void resizing(int newBucketsLength) {
        MyHashMap<K, V> temp = new MyHashMap<>(newBucketsLength);
        for (ArrayMap<K, V> bucket : this.buckets) {
            for (K key : bucket.keySet()) {
                V val = bucket.get(key);
                temp.put(key, val);
            }
        }
        this.buckets = temp.buckets;
        this.size = temp.size;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return this.size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (ArrayMap<K, V> bucket : this.buckets) {
            for (K key : bucket) {
                keySet.add(key);
            }
        }
        return keySet;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        int keyIndex = hash(key);
        if (this.buckets[keyIndex].containsKey(key)) {
            this.size -= 1;
        }
        return this.buckets[keyIndex].remove(key);
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        if (value == null) {
            return null;
        }
        int keyIndex = hash(key);
        int oldSize = this.buckets[keyIndex].size();
        V returnVal = this.buckets[keyIndex].remove(key, value);
        if (this.buckets[keyIndex].size() == oldSize - 1) {
            this.size -= 1;
        }
        return returnVal;
    }

    @Override
    public Iterator<K> iterator() {
        return this.keySet().iterator();
    }
}
