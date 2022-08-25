package lab11.graphs;

import edu.princeton.cs.algs4.In;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    private Deque<Integer> queue;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        this.queue = new ArrayDeque<>();

    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        this.marked[s] = true;
        announce();
        if (s == t) {
            return;
        }
        this.queue.add(this.s);
        while (!this.queue.isEmpty()) {
            int x = queue.poll();
            for (int w : this.maze.adj(x)) {
                if (!this.marked[w]) {
                    this.queue.add(w);
                    this.marked[w] = true;
                    this.distTo[w] = this.distTo[x] + 1;
                    this.edgeTo[w] = x;
                    announce();
                    if (w == t) {
                        return;
                    }
                }
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

