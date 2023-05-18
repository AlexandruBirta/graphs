package ro.unibuc.fmi.graph;


import java.util.ArrayList;
import java.util.List;


class State implements Comparable<State> {
    int vertex;
    int cost;
    int heuristic;
    List<Integer> path;

    public State(int vertex, int cost, int heuristic) {
        this.vertex = vertex;
        this.cost = cost;
        this.heuristic = heuristic;
        this.path = new ArrayList<>();
        this.path.add(vertex);
    }

    public State(int vertex, int cost, int heuristic, List<Integer> path) {
        this.vertex = vertex;
        this.cost = cost;
        this.heuristic = heuristic;
        this.path = path;
    }

    @Override
    public int compareTo(State other) {
        int totalCost = cost + heuristic;
        int otherTotalCost = other.cost + other.heuristic;
        return Integer.compare(totalCost, otherTotalCost);
    }

}
