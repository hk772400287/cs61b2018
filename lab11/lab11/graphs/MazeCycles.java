package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int cycleStart;
    private boolean isSetEdge = false;
    private boolean cycleFound = false;
    private Maze maze;


    public MazeCycles(Maze m) {
        super(m);
        maze = m;
    }

    @Override
    public void solve() {
        for (int s = 0; s < maze.N() * maze.N(); s++) {
            if (cycleFound) {
                break;
            }
            if (!marked[s]) {
                dfs(s, s);
            }
        }
    }

    // Helper methods go here
    private void dfs(int v, int prev) {
        this.marked[v] = true;
        for (int w : this.maze.adj(v)) {
            if (this.marked[w] && prev != w){
                this.edgeTo[w] = v;
                this.cycleStart = w;
                this.isSetEdge = true;
                this.cycleFound = true;
                announce();
                return;
            }
            if (!this.marked[w]) {
                dfs(w, v);
                if (!isSetEdge && cycleFound) {
                    return;
                } else if (!cycleFound) {
                    continue;
                }
                this.edgeTo[w] = v;
                announce();
                if (v == cycleStart) {
                    isSetEdge = false;
                }
                return;
            }
        }
    }
}

