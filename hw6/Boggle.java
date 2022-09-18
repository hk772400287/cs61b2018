

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MaxPQ;

import java.util.*;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "trivial_words.txt";

    /**
     * Solves a Boggle puzzle.
     * 
     * @param k The maximum number of words to return.
     * @param boardFilePath The file path to Boggle board file.
     * @return a list of words found in given Boggle board.
     *         The Strings are sorted in descending order of length.
     *         If multiple words have the same length,
     *         have them in ascending alphabetical order.
     */
    public static List<String> solve(int k, String boardFilePath) {
        Trie trie = new Trie();
        MaxPQ<String> pq = new MaxPQ<>(new cmp());
        In in = new In(boardFilePath);
        ArrayList<String> board = new ArrayList<>();
        while (in.hasNextLine()) {
            board.add(in.readLine());
        }
        int N = board.get(0).length();
        int M = board.size();
        char[][] chars = new char[N][M];
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                chars[i][j] = board.get(j).charAt(i);
            }
        }
        for (int j = 0; j < M; j++) {
            for (int i = 0; i < N; i++) {
                boolean[][] mark = new boolean[N][M];
                dfs("", new Position(i, j), trie, pq, chars, mark);
            }
        }
//      HashSet<String> hs = new HashSet<>();
        List<String> l = new ArrayList<>();
        int i = 0;
        while (!pq.isEmpty()) {
            String s = pq.delMax();
            if (i > 0 && s.equals(l.get(i - 1))) {
                continue;
            }
            l.add(s);
            i++;
            if (l.size() >= k) {
                break;
            }
        }
        return l;
    }

    private static class cmp implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if (o1.length() == o2.length()) {
                return o1.compareTo(o2) * -1;
            }
            return o1.length() - o2.length();
        }
    }

    private static class Position {
        private int x;
        private int y;
        private Position(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

//    private static void dfs(String s, Position p, Trie trie, MaxPQ<String> pq, char[][] chars, Boolean[][] mark) {
//        Stack<Position> stack = new Stack<>();
//        stack.add(p);
//        while (!stack.empty()) {
//            Position pos = stack.pop();
//            mark[pos.x][pos.y] = true;
//            if (trie.noPrune(s, chars[pos.x][pos.y])) {
//                s += chars[pos.x][pos.y];
//                if (trie.isAWord(s)) {
//                    pq.insert(s);
//                }
//                for (Position pp : neighbors(pos, chars)) {
//                    if (isInBoard(pp, chars) && !mark[pp.x][pp.y]) {
//                        stack.add(pp);
//                    }
//                }
//            }
//        }
//    }

    private static void dfs(String s, Position p, Trie trie, MaxPQ<String> pq, char[][] chars, boolean[][] mark) {
        mark[p.x][p.y] = true;
        if (!trie.noPrune(s, chars[p.x][p.y])) {
            mark[p.x][p.y] = false;
            return;
        }
        if (trie.isAWord(s + chars[p.x][p.y])) {
            pq.insert(s + chars[p.x][p.y]);
        }
        for (Position pos : neighbors(p, chars)) {
            if (isInBoard(pos, chars) && !mark[pos.x][pos.y]) {
                dfs(s + chars[p.x][p.y], pos, trie, pq, chars, mark);
            }
        }
        mark[p.x][p.y] = false;
    }

    private static List<Position> neighbors(Position p, char[][] chars) {
        List<Position> l = new ArrayList<>();
        l.add(new Position(p.x - 1, p.y - 1));
        l.add(new Position(p.x - 1, p.y));
        l.add(new Position(p.x - 1, p.y + 1));
        l.add(new Position(p.x, p.y - 1));
        l.add(new Position(p.x, p.y + 1));
        l.add(new Position(p.x + 1, p.y - 1));
        l.add(new Position(p.x + 1, p.y));
        l.add(new Position(p.x + 1, p.y + 1));
        return l;
    }

    private static Boolean isInBoard(Position p, char[][] chars) {
        int M = chars[0].length;
        int N = chars.length;
        return p.x >= 0 && p.x < N && p.y >= 0 && p.y < M;
    }

    public static void main(String[] args) {
        List<String> list = Boggle.solve(7, "exampleBoard2.txt");
        for (String s : list) {
            System.out.println(s);
        }
    }

}
