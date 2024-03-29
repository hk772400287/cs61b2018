import java.util.HashMap;
import edu.princeton.cs.algs4.In;

public class Trie {

    private Node trie;


    private static class Node {
        private Boolean isWord;
        private HashMap<Character, Node> next;
        private Node(Boolean isWord) {
            this.isWord = isWord;
            this.next = new HashMap<>();
        }
    }

    public Trie() {
        In in = new In(Boggle.dictPath);
        this.trie = new Node(false);
        while (in.hasNextLine()) {
            String word = in.readLine();
            put(this.trie, word);
        }
    }

    private void put(Node root, String word) {
        for (int i = 0; i < word.length(); i++) {
            char c = word.toCharArray()[i];
            if (!root.next.containsKey(c)) {
                root.next.put(c, new Node(false));
            }
            root = root.next.get(c);
        }
        root.isWord = true;
    }


    public boolean[] noPruneisWord(String s, char c) {
        boolean[] noPruneAndisWord = new boolean[2];
        Node r = this.trie;
        for (int i = 0; i < s.length(); i++) {
            char ch = s.toCharArray()[i];
            r = r.next.get(ch);
        }
        noPruneAndisWord[0] = r.next.containsKey(c);
        if (noPruneAndisWord[0]) {
            noPruneAndisWord[1] = r.next.get(c).isWord;
        }
        return noPruneAndisWord;
    }
}
