import edu.princeton.cs.algs4.Picture;

import java.awt.*;

public class SeamCarver {
    private Picture picture;
    public SeamCarver(Picture picture) {
        this.picture = new Picture(picture);
    }
    public Picture picture() {
        return new Picture(this.picture);
    }
    public int width() {
        return this.picture.width();
    }
    public int height() {
        return this.picture.height();
    }
    public double energy(int x, int y) {
        if (x < 0 || x > this.width() - 1 || y < 0 || y > this.height() - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        Color left = null;
        Color right = null;
        Color up = null;
        Color down = null;
        if (x == 0) {
            left = this.picture.get(this.width() - 1, y);
        } else {
            left = this.picture.get(x - 1, y);
        }
        if (x == this.width() - 1) {
            right = this.picture.get(0, y);
        } else {
            right = this.picture.get(x + 1, y);
        }
        if (y == 0) {
            up = this.picture.get(x, this.height() - 1);
        } else {
            up = this.picture.get(x, y - 1);
        }
        if (y == this.height() - 1) {
            down = this.picture.get(x, 0);
        } else {
            down = this.picture.get(x, y + 1);
        }
        int xGradient = computeGradient(left, right);
        int yGradient = computeGradient(up, down);
        return xGradient + yGradient;
    }

    private int computeGradient(Color a, Color b) {
        int ared = a.getRed();
        return (a.getRed() - b.getRed()) * (a.getRed() - b.getRed())
                + (a.getGreen() - b.getGreen()) * (a.getGreen() - b.getGreen())
                + (a.getBlue() - b.getBlue()) * (a.getBlue() - b.getBlue());
    }

    private double setMinAndEdge(int range1, int range2, double[][] M, int[][] edgeTo, int x, int y) {
        double min = Integer.MAX_VALUE;
        for (int i = range1; i <= range2; i++) {
            if (M[i][y - 1] < min) {
                min = M[i][y - 1];
                edgeTo[x][y] = i;
            }
        }
        return min;
    }
    public int[] findVerticalSeam() {
        double[][] M = new double[this.width()][this.height()];
        int[][] edgeTo = new int[this.width()][this.height()];
        for (int i = 0; i < this.width(); i++) {
            M[i][0] = energy(i, 0);
            edgeTo[i][0] = i;
        }
        double min;
        for (int y = 1; y < this.height(); y++) {
            for (int x = 0; x < this.width(); x++) {
                if (this.width() == 1) {
                    min = setMinAndEdge(x, x, M, edgeTo, x, y);
                } else if (x == 0) {
                    min = setMinAndEdge(x, x + 1, M, edgeTo, x, y);
                } else if (x == width() - 1) {
                    min = setMinAndEdge(x - 1, x, M, edgeTo, x, y);
                } else {
                    min = setMinAndEdge(x - 1, x + 1, M, edgeTo, x, y);
                }
                M[x][y] = this.energy(x, y) + min;
            }
        }
        double minOfM = Integer.MAX_VALUE;
        int index = 0;
        for (int x = 0; x < this.width(); x++) {
            if (M[x][this.height() - 1] < minOfM) {
                minOfM = M[x][this.height() - 1];
                index = x;
            }
        }
        int[] minPath = new int[this.height()];
        for (int r = this.height() - 1; r >= 0; r--) {
            minPath[r] = index;
            index = edgeTo[index][r];
        }
        return minPath;
    }
    public int[] findHorizontalSeam() {
        this.picture = rotate90(this.picture, "right");
        int[] minPath = this.findVerticalSeam();
        this.picture = rotate90(this.picture, "left");
        for (int i = 0; i < minPath.length; i++) {
            minPath[i] = this.height() - 1 - minPath[i];
        }
        return minPath;
    }

    private Picture rotate90(Picture original, String direction) {
        Picture dest = new Picture(original.height(), original.width());
        for (int y = 0; y < original.height(); y++) {
            for (int x = 0; x < original.width(); x++) {
                if (direction.equals("right")) {
                    dest.set(original.height() - 1 - y, x, original.get(x, y));
                } else if (direction.equals("left")) {
                    dest.set(y, original.width() - 1 - x, original.get(x, y));
                }
            }
        }
        return dest;
    }
    public void removeHorizontalSeam(int[] seam) {
        SeamRemover.removeHorizontalSeam(this.picture, seam);
    }

    public void removeVerticalSeam(int[] seam) {
        SeamRemover.removeVerticalSeam(this.picture, seam);
    }

//    public static void main(String[] args) {
//        Picture p = new Picture("images/1x8.png");
//        SeamCarver sc = new SeamCarver(p);
//        sc.findVerticalSeam();
//        sc.findHorizontalSeam();
//        System.out.println(sc.picture().equals(p));
//    }
}
