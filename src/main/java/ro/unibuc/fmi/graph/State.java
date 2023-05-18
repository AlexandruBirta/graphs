package ro.unibuc.fmi.graph;


import java.util.Objects;


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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return gScore == state.gScore && fScore == state.fScore && Objects.equals(vertex, state.vertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vertex, gScore, fScore);
    }

    @Override
    public int compareTo(State other) {
        return Integer.compare(this.fScore, other.fScore);
    }

}