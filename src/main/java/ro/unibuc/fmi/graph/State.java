package ro.unibuc.fmi.graph;


class State implements Comparable<State> {
    Vertex vertex;
    int gScore;
    int fScore;

    public State(Vertex vertex, int gScore, int fScore) {
        this.vertex = vertex;
        this.gScore = gScore;
        this.fScore = fScore;
    }

    @Override
    public int compareTo(State other) {
        return Integer.compare(this.fScore, other.fScore);
    }
}