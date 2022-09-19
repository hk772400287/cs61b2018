

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MaxPQ;

import java.util.*;

public class Boggle {
    
    // File path of dictionary file
    static String dictPath = "words.txt";

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
        if (k < 0) {
            throw new IllegalArgumentException("k is non-positive");
        }
        Trie trie = new Trie();
//        MaxPQ<String> pq = new MaxPQ<>(new CMP());
        List<String> l = new ArrayList<>();
        In in = new In(boardFilePath);
        ArrayList<String> board = new ArrayList<>();
        while (in.hasNextLine()) {
            board.add(in.readLine());
        }
        int N = board.get(0).length();
        for (String s : board) {
            if (s.length() != N) {
                throw new IllegalArgumentException("The input board is not rectangular.");
            }
        }
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
                dfs("", i, j, trie, l, chars, mark);
            }
        }
        l.sort(new CMP());
        List<String> returnList = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < l.size(); i++) {
            if (i > 0 && l.get(i).equals(returnList.get(index - 1))) {
                continue;
            }
            returnList.add(index, l.get(i));
            index++;
            if (returnList.size() >= k) {
                break;
            }
        }
        return returnList;
    }

    private static class CMP implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            if (o1.length() == o2.length()) {
                return o1.compareTo(o2);
            }
            return o2.length() - o1.length();
        }
    }


//    private static void dfs(String s, Position p, Trie trie, MaxPQ<String> pq, char[][] chars, Boolean[][] mark) {
//        Stack<Stack<Position>> stack = new Stack<>();
//        stack.add(new Stack<>());
//        stack.peek().add(p);
//        while (!stack.empty()) {
//            Position pos = stack.peek().pop();
//            mark[pos.x][pos.y] = true;
//            if (trie.noPrune(s, chars[pos.x][pos.y])) {
//                s += chars[pos.x][pos.y];
//                if (trie.isAWord(s)) {
//                    pq.insert(s);
//                }
//                for (Position neighbor : neighbors(pos, chars)) {
//                    if (isInBoard(neighbor, chars) && !mark[neighbor.x][neighbor.y]) {
//                        Stack<Position> innerStack = new Stack<>();
//                        innerStack.add(neighbor);
//                        stack.add(innerStack);
//                    }
//                }
//            }
//        }
//    }

    private static void dfs(String s, int x, int y, Trie trie, List<String> list, char[][] chars, boolean[][] mark) {
        mark[x][y] = true;
        boolean[] npiw = trie.noPruneisWord(s, chars[x][y]);
        if (!npiw[0]) {
            mark[x][y] = false;
            return;
        }
        if (npiw[1]) {
            list.add(s + chars[x][y]);
        }
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                if (isInBoard(i, j, chars) && !mark[i][j]) {
                    dfs(s + chars[x][y], i, j, trie, list, chars, mark);
                }
            }
        }
        mark[x][y] = false;
    }


    private static boolean isInBoard(int x, int y, char[][] chars) {
        int M = chars[0].length;
        int N = chars.length;
        return x >= 0 && x < N && y >= 0 && y < M;
    }

    public static void main(String[] args) {
        List<String> list = Boggle.solve(7, "exampleBoard.txt");
        for (String s : list) {
            System.out.println(s);
        }
    }

}
