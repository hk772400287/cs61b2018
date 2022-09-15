import edu.princeton.cs.algs4.MinPQ;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class BinaryTrie implements Serializable {
    private Node trie;

    private static class Node implements Comparable<Node>, Serializable {
        private char ch;
        private int fre;
        private Node left;
        private Node right;
        private int code;
        public Node(char ch, int fre, Node left, Node right) {
            this.ch = ch;
            this.fre = fre;
            this.left = left;
            this.right = right;
        }
        @Override
        public int compareTo(Node that) {
            return this.fre - that.fre;
        }
    }
    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        MinPQ<Node> pq = new MinPQ<>();
        for (char c : frequencyTable.keySet()) {
            pq.insert(new Node(c, frequencyTable.get(c), null, null));
        }
        while (pq.size() > 1) {
            Node left = pq.delMin();
            left.code = 0;
            Node right = pq.delMin();
            right.code = 1;
            Node parent = new Node('\0', left.fre + right.fre, left, right);
            pq.insert(parent);
        }
        this.trie = pq.delMin();
    }


    public Match longestPrefixMatch(BitSequence querySequence) {
        Node r = this.trie;
        String s = "";
        for (int i = 0; i < querySequence.length(); i++) {
            int bit = querySequence.bitAt(i);
            s += bit;
            if (r.left.code == bit) {
                r = r.left;
            } else {
                r = r.right;
            }
            if (r.right == null && r.left == null) {
                break;
            }
        }
        return new Match(new BitSequence(s), r.ch);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> lookupTable = new HashMap<>();
        String s = "";
        buildLookupTableHelper(this.trie, s, lookupTable);
        return lookupTable;
    }

    private void buildLookupTableHelper(Node r, String s, Map<Character, BitSequence> table) {
        if (r.left == null && r.right == null) {
            table.put(r.ch, new BitSequence(s));
            return;
        }
        buildLookupTableHelper(r.left, s + r.left.code, table);
        buildLookupTableHelper(r.right, s + r.right.code, table);
    }
}