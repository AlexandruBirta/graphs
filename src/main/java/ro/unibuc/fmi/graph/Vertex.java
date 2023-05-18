package ro.unibuc.fmi.graph;


import java.util.Objects;


public class Vertex {

    public Integer label;

    public Vertex(Integer label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(label, vertex.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

}
