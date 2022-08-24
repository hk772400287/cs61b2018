package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {

    private int[][] ws;
    private static int BLANK = 0;
    public Board(int[][] tiles) {
        int[][] copy = new int[tiles.length][tiles.length];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        this.ws = copy;
    }

    public int tileAt(int i, int j) {
        if (i < 0 || i > ws[i].length || j < 0 || j > ws[i].length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return ws[i][j];
    }
    public int size() {
        return this.ws[0].length;
    }
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }
    public int hamming() {
        int N = this.size();
        int v = 1;
        int count = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tileAt(i, j) != v && tileAt(i, j) != 0) {
                    count++;
                }
                v++;
            }
        }
        return count;

    }
    public int manhattan() {
        int count = 0;
        int N = this.size();
        int goalValI;
        int goalValJ;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int val = this.ws[i][j];
                if (val != 0) {
                    if (val % N == 0) {
                        goalValI = val / N - 1;
                        goalValJ = N - 1;
                    } else {
                        goalValI = val / N;
                        goalValJ = val % N - 1;
                    }
                    count += Math.abs(i - goalValI) + Math.abs(j - goalValJ);
                }
            }
        }
        return count;
    }

    public int estimatedDistanceToGoal() {
        return this.manhattan();
    }

    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || this.getClass() != y.getClass()) {
            return false;
        }
        Board that = (Board) y;
        if (this.size() != that.size()) {
            return false;
        }
        int N = this.size();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tileAt(i, j) != that.tileAt(i, j)) {
                    return false;
                }
            }
        }
        return true;

    }

    @Override
    public int hashCode() {
        int hc = 0;
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < this.size(); j++) {
                hc = hc * 31;
                hc += tileAt(i, j);
            }
        }
        return hc;
    }

    /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
