package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF uf;
    private WeightedQuickUnionUF uf2;
    private boolean[][] trackIsOpen;

    private int openSize;

    private int N;

    public Percolation(int N) {
        // create N-by-N grid, with all sites initially blocked
        if (N > 0) {
            this.N = N;
            openSize = 0;
            uf = new WeightedQuickUnionUF(N * N + 2);
            uf2 = new WeightedQuickUnionUF(N * N + 1);
            for (int i = xyTo1D(0, 0); i < N; i++) {
                uf.union(i, N * N);
                uf2.union(i, N * N);
            }
            for (int i = xyTo1D(N - 1, 0); i < N * N; i++) {
                uf.union(i, N * N + 1);
            }
            trackIsOpen = new boolean[N][N];
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    trackIsOpen[i][j] = false;
                }
            }
        } else {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    private int xyTo1D(int r, int c) {
        return r * N + c;
    }

    public void open(int row, int col) {
        // open the site (row, col) if it is not open already
        if (isIndexValid(row, col)) {
            if (!isOpen(row, col)) {
                trackIsOpen[row][col] = true;
                openSize += 1;
                int number = xyTo1D(row, col);
                judgeAndUnion(number, row, col - 1);
                judgeAndUnion(number, row, col + 1);
                judgeAndUnion(number, row - 1, col);
                judgeAndUnion(number, row + 1, col);
            }
        } else {
            throw new java.lang.IndexOutOfBoundsException("Invalid index");
        }
    }

    private Boolean isIndexValid(int row, int col) {
        return row >= 0 && row < N && col >= 0 && col < N;
    }

    private void judgeAndUnion(int number, int row, int col) {
        if (isIndexValid(row, col)) {
            if (isOpen(row, col)) {
                int numberOfNeighbor = xyTo1D(row, col);
                uf.union(number, numberOfNeighbor);
                uf2.union(number, numberOfNeighbor);
            }
        }
    }


    public boolean isOpen(int row, int col) {
        // is the site (row, col) open?
        if (isIndexValid(row, col)) {
            return trackIsOpen[row][col];
        }
        throw new java.lang.IndexOutOfBoundsException("Invalid index");
    }
    public boolean isFull(int row, int col) {
        // is the site (row, col) full?
        if (isIndexValid(row, col)) {
            if (isOpen(row, col)) {
                int number = xyTo1D(row, col);
                return uf2.connected(number, N * N);
            } else {
                return false;
            }
        }
        throw new java.lang.IndexOutOfBoundsException("Invalid index");
    }
    public int numberOfOpenSites() {
        // number of open sites
        return openSize;
    }
    public boolean percolates() {
        // does the system percolate?
        if (N == 1) {
            return isOpen(0, 0);
        }
        return uf.connected(N * N, N * N + 1);
    }
    public static void main(String[] args) {
        // use for unit testing (not required)
        Percolation p = new Percolation(3);
        p.open(0, 0);
        p.open(1, 1);
        System.out.println(p.uf.find(4));
        System.out.println(p.uf.find(0));
        System.out.println(p.uf.find(1));
    }


}
