package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;
    private MinPQ<Integer> pq;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        pq = new MinPQ<>(new cmp());
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return  Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    private class cmp implements Comparator<Integer> {
        @Override
        public int compare(Integer x, Integer y) {
            return h(x) + distTo[x] - h(y) - distTo[y];
        }
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        marked[s] = true;
        announce();
        pq.insert(s);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            if (v == t) {
                targetFound = true;
                break;
            }
            for (int w : this.maze.adj(v)) {
                if (!marked[w]) {
                    marked[w] = true;
                    edgeTo[w] = v;
                    distTo[w] = distTo[v] + 1;
                    announce();
                    pq.insert(w);
                } else if (marked[w] && w != edgeTo[v]) {
                    if (distTo[v] + 1 < distTo[w]) {
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        announce();
                    }
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

