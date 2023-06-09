package ro.unibuc.fmi.graph;


import java.util.Objects;


public class Vertex implements Comparable {

    public Integer label;
    double distance;
    double g;
    double h;
    double f;

    int color;

    Vertex parent;

    public Vertex(Integer label) {
        this.label = label;
        this.distance = Float.MAX_VALUE;
        this.g = 0;
        this.h = 0;
        this.f = 0;
        this.parent = null;
        this.color=-1;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public Vertex getParent() {
        return parent;
    }

    public void setParent(Vertex parent) {
        this.parent = parent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return Double.compare(vertex.distance, distance) == 0 && Double.compare(vertex.g, g) == 0 && Double.compare(vertex.h, h) == 0 && Double.compare(vertex.f, f) == 0 && Objects.equals(label, vertex.label) && Objects.equals(parent, vertex.parent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(label, distance, g, h, f, parent);
    }

    @Override
    public String toString() {
        return label.toString();
    }

    public int compareTo(Object o) {
        Vertex other = (Vertex) o;
        return Double.compare(this.distance, other.distance);
    }

}
