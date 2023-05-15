package ro.unibuc.fmi.graph;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Graph {

    private Map<Vertex, List<Vertex>> adjVertices;

    public Graph() {
        this.adjVertices = new HashMap<>();
    }

    public void addVertex(String label) {
        adjVertices.putIfAbsent(new Vertex(label), new ArrayList<>());
    }

    public void removeVertex(String label) {
        Vertex v = new Vertex(label);
        adjVertices.values().forEach(e -> e.remove(v));
        adjVertices.remove(new Vertex(label));
    }

    public void addEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        adjVertices.get(v1).add(v2);
        adjVertices.get(v2).add(v1);
    }

    public void removeEdge(String label1, String label2) {
        Vertex v1 = new Vertex(label1);
        Vertex v2 = new Vertex(label2);
        List<Vertex> eV1 = adjVertices.get(v1);
        List<Vertex> eV2 = adjVertices.get(v2);
        if (eV1 != null)
            eV1.remove(v2);
        if (eV2 != null)
            eV2.remove(v1);
    }

    public List<Vertex> getAdjVertices(String label) {
        return adjVertices.get(new Vertex(label));
    }

    public int findDegree(String label) {
        return adjVertices.get(new Vertex(label)).size();
    }

    public boolean areVerticesAdjacent(String label1, String label2) {
        return adjVertices.get(new Vertex(label1)).contains(new Vertex(label2));
    }

    public String printEdges() {

        StringBuffer sb = new StringBuffer();

        for (Vertex v : adjVertices.keySet()) {
            for (Vertex w : adjVertices.get(v)) {
                sb.append(v).append(" ").append(w).append('\n');
            }
        }

        return sb.toString();

    }

    public String printGraph() {

        StringBuffer sb = new StringBuffer();

        for (Vertex v : adjVertices.keySet()) {
            sb.append(v).append(adjVertices.get(v)).append('\n');
        }

        return sb.toString();

    }

    class Vertex {

        String label;

        Vertex(String label) {
            this.label = label;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + getOuterType().hashCode();
            result = prime * result + ((label == null) ? 0 : label.hashCode());
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Vertex other = (Vertex) obj;
            if (!getOuterType().equals(other.getOuterType()))
                return false;
            if (label == null) {
                if (other.label != null)
                    return false;
            } else if (!label.equals(other.label))
                return false;
            return true;
        }

        @Override
        public String toString() {
            return label;
        }


        private Graph getOuterType() {
            return Graph.this;
        }

    }

}