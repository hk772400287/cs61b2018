package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import java.util.Comparator;


public class Solver {

    private Stack<WorldState> sol;
    private SearchNode goal;

    private static class SearchNode {
        private WorldState ws;
        private int movesSoFar;
        private SearchNode prev;

        private int priority;
        public SearchNode(WorldState ws, int movesSoFar, SearchNode prev) {
            this.ws = ws;
            this.movesSoFar = movesSoFar;
            this.prev = prev;
            this.priority = movesSoFar + ws.estimatedDistanceToGoal();
        }
    }

    private static class CMP implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode a, SearchNode b) {
            return a.priority - b.priority;
        }
    }

    public Solver(WorldState initial) {
        this.sol = new Stack<>();
        MinPQ<SearchNode> mPQ = new MinPQ<>(new CMP());
        SearchNode initSeachNode = new SearchNode(initial, 0, null);
        mPQ.insert(initSeachNode);
        while (true) {
            SearchNode x = mPQ.delMin();
            if (x.ws.isGoal()) {
                this.goal = x;
                while (x != null) {
                    this.sol.push(x.ws);
                    x = x.prev;
                }
                break;
            }
            for (WorldState neighbor : x.ws.neighbors()) {
                if (x.prev == null || !neighbor.equals(x.prev.ws)) {
                    mPQ.insert(new SearchNode(neighbor, x.movesSoFar + 1, x));
                }
            }
        }
    }
    public int moves() {
        return this.goal.movesSoFar;
    }

    public Iterable<WorldState> solution() {
        return this.sol;
    }
}
