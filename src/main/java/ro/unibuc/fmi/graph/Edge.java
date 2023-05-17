package ro.unibuc.fmi.graph;


import java.util.Objects;


public class Edge {

    Vertex sourceVertex;
    Vertex destinationVertex;

    public Edge() {

    }

    public Edge(Vertex sourceVertex, Vertex destinationVertex) {
        this.sourceVertex = sourceVertex;
        this.destinationVertex = destinationVertex;
    }

    public void setDestinationVertex(Vertex destinationVertex) {
        this.destinationVertex = destinationVertex;
    }
    public Vertex getDestinationVertex() {
        return destinationVertex;
    }

    public void setSourceVertex(Vertex sourceVertex) {
        this.sourceVertex = sourceVertex;
    }
    public Vertex getSourceVertex() {
        return sourceVertex;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(sourceVertex, edge.sourceVertex) && Objects.equals(destinationVertex, edge.destinationVertex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceVertex, destinationVertex);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "sourceVertex=" + sourceVertex +
                ", destinationVertex=" + destinationVertex +
                '}';
    }

}
